expect(true).toBe(true);

const jsonFileParser  = require("../jsonFileParser.js");
const queriesManager  = require("../queriesManager.js");
const databaseManager = require("../databaseManager.js");
const statusTypes     = require("../enums/statusTypes.js");
const assert          = require("assert");

const stream_of_statuses = jsonFileParser("asset_statuses.json");

let ids_db = [];        
let statuses_db = {[statusTypes.NORMAL]: [], [statusTypes.WARNING]: [], [statusTypes.ERROR]: []};

stream_of_statuses.forEach(new_status => {
    ids_db, statuses_db, _ = databaseManager.updateDatabases(ids_db, statuses_db, new_status);
});

// Note does not test bad paths. Bad paths should be caught by unit tests though
describe("Output for assignment", () => {        

    it("should return correct results for getAssetStatusesByAssetIDs(assetIds)", () => {  

    const correct_results = [
        { id: '8d7605e5-68b0-4502-ae46-d98e8a291acd',
        assetId: 'f1aa3b54-6238-4e67-b3ea-a5bc13b77dd0',
        statusType: 'ERROR',
        createdAt: '2018-07-18T03:59:49Z' },
      { id: 'be8e18db-5eb2-4d19-94bd-de4864002a1d',
        assetId: 'fbbcaf29-a72f-48f7-8f2b-d5abf8bc24bf',
        statusType: 'NORMAL',
        createdAt: '2018-08-25T20:38:47Z' },
      { id: 'bea124fd-b7ed-44bb-a370-76c5ae161f27',
        assetId: '4f436a0d-903b-46bd-8e45-46c1dd3dc631',
        statusType: 'WARNING',
        createdAt: '2018-08-29T06:03:56Z' } ]

    const results = queriesManager.getAssetStatusesByAssetIDs(["f1aa3b54-6238-4e67-b3ea-a5bc13b77dd0",
        "fbbcaf29-a72f-48f7-8f2b-d5abf8bc24bf",
        "4f436a0d-903b-46bd-8e45-46c1dd3dc631"], ids_db);
         
    assert.deepEqual(results, correct_results); // tests for object equality
    });

    it("should return correct results for getAssetStatusesByAssetIDs('ERROR')", () => {  
        const results = queriesManager.getAssetIdsByStatusType(statusTypes.ERROR, statuses_db);        
        const correct_results = [ 
          { id: '0f8d7ce2-383c-452f-9964-2a131552c1f8',
            assetId: '18a5ba9a-7a0f-4c09-a836-8e2ef2da79ca',
            statusType: 'ERROR',
            createdAt: '2018-06-01T03:29:06Z' },
          { id: '8d7605e5-68b0-4502-ae46-d98e8a291acd',
            assetId: 'f1aa3b54-6238-4e67-b3ea-a5bc13b77dd0',
            statusType: 'ERROR',
            createdAt: '2018-07-18T03:59:49Z' } ]
        
        assert.deepEqual(results, correct_results);
    });

    it("should return correct results for getAssetStatusesByAssetIDs('WARNING')", () => {  
            
    const results = queriesManager.getAssetIdsByStatusType(statusTypes.WARNING, statuses_db);
    const correct_results = [ 
  { id: '0fbfc8c1-33a4-4861-a2be-f5e0fe30b22b',
    assetId: '8786cc43-230d-4ac9-8827-5287f4bfa7d8',
    statusType: 'WARNING',
    createdAt: '2018-04-02T20:45:03Z' },
  { id: '4e095a93-c0d7-46bc-a22d-b8d242301216',
    assetId: '67247f96-1b1a-48e5-82b4-5277d4438b3c',
    statusType: 'WARNING',
    createdAt: '2018-04-17T19:51:02Z' },
  { id: '51bfd69d-4aea-482c-b223-2d0549d4f11e',
    assetId: '5cd0c05f-2a39-4ea5-b0ea-0dedb3e5b968',
    statusType: 'WARNING',
    createdAt: '2017-12-23T16:52:28Z' },
  { id: '9331b2dd-c113-45c9-acce-74b8e1a23f67',
    assetId: 'e4c52414-9776-451d-ba80-f3f46b77686b',
    statusType: 'WARNING',
    createdAt: '2018-04-05T15:22:12Z' },
  { id: '7a63e8b9-4f68-4d48-800f-eb4e1371497a',
    assetId: '12a594e4-f51f-407e-9d01-679a8323a3c0',
    statusType: 'WARNING',
    createdAt: '2018-04-22T23:03:09Z' },
  { id: '73afb8e0-cb8c-4a07-a137-fb7a5f94722b',
    assetId: '91acbf58-e7ea-4790-b58a-7442d2a32109',
    statusType: 'WARNING',
    createdAt: '2018-05-07T21:23:41Z' },
  { id: 'e90c785a-8918-4ccd-9dec-9c99dd346c98',
    assetId: '62d98170-ff7e-4ee1-8f1c-b54f8aed351c',
    statusType: 'WARNING',
    createdAt: '2018-08-20T02:18:36Z' },
  { id: '84d5aa27-1476-40ba-9689-63642b36464a',
    assetId: '8fded256-979f-402c-aacc-8ab6b98787d1',
    statusType: 'WARNING',
    createdAt: '2018-08-21T19:50:06Z' },
  { id: 'ddb464fd-5223-429c-843f-720aaa4fcb8c',
    assetId: 'd4250e33-f81f-408f-855a-36414fe4cee5',
    statusType: 'WARNING',
    createdAt: '2018-08-24T17:24:39Z' },
  { id: 'bea124fd-b7ed-44bb-a370-76c5ae161f27',
    assetId: '4f436a0d-903b-46bd-8e45-46c1dd3dc631',
    statusType: 'WARNING',
    createdAt: '2018-08-29T06:03:56Z' } ]
        
    assert.deepEqual(results, correct_results);
    });      
});