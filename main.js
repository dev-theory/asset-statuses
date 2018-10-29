const eventNames      = require("./enums/eventNames.js");
const statusTypes     = require("./enums/statusTypes.js");
const jsonFileParser  = require("./jsonFileParser.js");
const queriesManager  = require("./queriesManager.js");
const Consumer        = require("./consumer.js");
const notifyConsumers  = require("./notifyConsumers.js");
const databaseManager = require("./databaseManager.js");
const eventManager    = require("./eventManager.js");
const consoleManager  = require("./consoleManager.js");

// file_to_read: filename containing array of asset statuses
module.exports = (file_to_read) => {
  
    // Get all asset_statuses from asset_statuses.json
    // Do not need to validate each assetStatus, was told I can assume they are correct      
    const stream_of_statuses = jsonFileParser(file_to_read);        

    let ids_db = [];        
    let statuses_db = {[statusTypes.NORMAL]: [], [statusTypes.WARNING]: [], [statusTypes.ERROR]: []};  

    // consumer name is not used/required but thought it made sense to have
    let consumer1 = new Consumer("paul");
    consumer1.subscribeToEvents([eventNames.AssetFailed, eventNames.AssetMayFail, eventNames.AssetRecovered, eventNames.AssetFailedAbruptly]);

    // showing that events are not output for consumer kyle since they are not subscribed to any
    let consumer2 = new Consumer("kyle");
    consumer2.subscribeToEvents([]);
    consumers = [consumer1, consumer2];

    // Mock real-time queue
    // Assume that I can process events faster than they can occur
    stream_of_statuses.forEach(new_status => {            
    
          ids_db, statuses_db, old_status = databaseManager.updateDatabases(ids_db, statuses_db, new_status);
                        
          let event = eventManager(old_status, new_status, eventNames, statusTypes);

          notifyConsumers(event, new_status, consumers);      
    });

    // Query database for assignment
    let results = queriesManager.getAssetStatusesByAssetIDs(["f1aa3b54-6238-4e67-b3ea-a5bc13b77dd0",
        "fbbcaf29-a72f-48f7-8f2b-d5abf8bc24bf",
        "4f436a0d-903b-46bd-8e45-46c1dd3dc631"], ids_db);

    consoleManager.logDifferentAssetIdAndStatusType(results);
      
    results = queriesManager.getAssetIdsByStatusType(statusTypes.ERROR, statuses_db);
    consoleManager.logAssetIdsGivenStatusType(results);
        
    results = queriesManager.getAssetIdsByStatusType(statusTypes.WARNING, statuses_db);
    consoleManager.logAssetIdsGivenStatusType(results);
}