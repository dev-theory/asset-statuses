const jsonFileParser  = require("../jsonFileParser.js");
const Stopwatch       = require("node-stopwatch").Stopwatch;
const queriesManager  = require("../queriesManager.js");
const databaseManager = require("../databaseManager.js");
const statusTypes     = require("../enums/statusTypes.js");

const stream_of_statuses = jsonFileParser("asset_statuses.json");

let ids_db = [];        
let statuses_db = {[statusTypes.NORMAL]: [], [statusTypes.WARNING]: [], [statusTypes.ERROR]: []};  

stream_of_statuses.forEach(new_status => {
    ids_db, statuses_db, _ = databaseManager.updateDatabases(ids_db, statuses_db, new_status);
});

describe("database queries performance tests", () => {

    // Run 10 iterations and take the best result because I'm on windows
    const take_best_over_x_attempts = 10 
    const iterations = 5000;
    let id_milliseconds_to_complete = 45;
    const stopwatch = Stopwatch.create();
    it("should complete " + iterations + " runs of getAssetStatusesByAssetIDs in less than " + id_milliseconds_to_complete + " milliseconds", () => {

        // Last item in database, should take longest to search for
        asset_normal = {
            "id": "a3954db4-7211-4005-837a-78aa405457eb",
            "assetId": "08b86fa4-b2be-4005-93e9-35d08b4c17a7",
            "statusType": "NORMAL",
            "createdAt": "2018-08-30T10:20:42Z"};
        
        let run_times = [];
        for(let j = 0; j < take_best_over_x_attempts; j++){
        
            stopwatch.start();
               
            for(let i = 0; i < iterations; i++){

                latest_statuses = queriesManager.getAssetStatusesByAssetIDs([asset_normal],ids_db);
            }
            
            stopwatch.stop();                    
            run_times.push(stopwatch.elapsedMilliseconds);
        }        

        // console.log("run times for getAssetStatusesByAssetIDs " + run_times); // for seeing run times
        const min_run_time = run_times.sort()[0];                
        expect(min_run_time).toBeLessThan(id_milliseconds_to_complete);
    });

    const status_milliseconds_to_complete = 20;
    it("should complete " + iterations + " runs of getAssetIdsByStatusType in less than " + status_milliseconds_to_complete + " milliseconds", () => {                        
        let run_times = [];

        for(let j = 0; j < take_best_over_x_attempts; j++){

            stopwatch.start();
            for(let i = 0; i < iterations; i++){

                // Should take the most amount of time getting the ERROR results
                all_assets_with_given_status = queriesManager.getAssetIdsByStatusType("ERROR", statuses_db);
            }
            stopwatch.stop();
            run_times.push(stopwatch.elapsedMilliseconds);
        }
        
        //console.log("run times for getAssetIdsByStatusType " + run_times); // for seeing run times
        const min_run_time = run_times.sort()[0];           
        expect(min_run_time).toBeLessThan(status_milliseconds_to_complete);
    });
});