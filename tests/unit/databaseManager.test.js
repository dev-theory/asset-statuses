const databaseManager = require("../../databaseManager.js");
const statusTypes     = require("../../enums/statusTypes.js");

const asset_1_normal  = {"assetId": 1, "statusType": statusTypes.NORMAL};
const asset_1_warning = {"assetId": 1, "statusType": statusTypes.WARNING};
const asset_1_error   = {"assetId": 1, "statusType": statusTypes.ERROR};
const asset_2         = {"assetId": 2, "statusType": statusTypes.WARNING};
const asset_3         = {"assetId": 3, "statusType": statusTypes.NORMAL};
const asset_4         = {"assetId": 4, "statusType": statusTypes.ERROR};

describe("update ids database", () => {            

    it("should push new asset to database and return old status = null when asset not in database", () => {
        
        let ids_db = [];        
        
        ids_db, old_status = databaseManager.updateIDsDB(ids_db, asset_1_normal);

        expect(ids_db).toEqual([asset_1_normal]);

        expect(old_status).toEqual(null);
    });

    it("should put/update new asset in database and return old_status = existing statusType \
when asset exists in database", () => {
        
        let ids_db = [asset_1_warning, asset_2];

        ids_db, old_status = databaseManager.updateIDsDB(ids_db, asset_1_normal);

        expect(ids_db).toEqual([asset_1_normal, asset_2]);

        expect(old_status).toEqual(statusTypes.WARNING);
    });
});

describe("update statuses database", () => {        

    // Not necessary with other test, left in case we make changes later
    it("should push new asset to database when asset is not in database", () => {        

        statuses_db = {[statusTypes.NORMAL]: [], [statusTypes.WARNING]: [], [statusTypes.ERROR]: []}; // empty database

        statuses_db = databaseManager.updateStatusesDB(statuses_db, asset_1_normal);

        expect(statuses_db).toEqual({[statusTypes.NORMAL]: [asset_1_normal], [statusTypes.WARNING]: [], [statusTypes.ERROR]: []});
    });

    // Test is a little verbose to check that WARNING array is not touched in this test
    // and correct index is deleted in ERROR array
    it("should push new asset to database and delete from other statusTypes when asset exists in database", () => {

        statuses_db = {[statusTypes.NORMAL]: [asset_3], [statusTypes.WARNING]: [asset_2],[statusTypes.ERROR]: [asset_1_error, asset_4]};

        statuses_db = databaseManager.updateStatusesDB(statuses_db, asset_1_normal);

        expect(statuses_db).toEqual({[statusTypes.NORMAL]: [asset_3, asset_1_normal], [statusTypes.WARNING]: [asset_2], [statusTypes.ERROR]: [asset_4]});
    });
});