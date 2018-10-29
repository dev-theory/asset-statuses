module.exports = {    
                
    // returns array of asset statuses given array of assetIds
    // input_ids: Array of asset_status["assetIds"]
    // ids_db: Database (array) containing set of unique asset status ids
    getAssetStatusesByAssetIDs: (input_ids, ids_db) => {
        
        // Should add some error handling here for incorrectly entered input_ids or ids_db

        let latest_asset_statuses = [];
        
        input_ids.forEach(input_id => {

            // loop through the ids_db and find the index where the assetIds property matches                  
            const index = ids_db.findIndex(asset_in_db => asset_in_db["assetId"] === input_id);
            let current_asset_status = null;
            
            // If the asset is not found, want to return a null if the asset isn't found
            if (index === -1){

                current_asset_status = {
                    "id": null,
                    "assetId": input_id,
                    "statusType": null,
                    "createdAt": null
                    }                
            }
            
            // If the asset id is found in the database
            else {

                current_asset_status = ids_db[index];
            }

            latest_asset_statuses.push(current_asset_status);                 
        });

        return latest_asset_statuses;            
    },  

    // returns array of asset ids given the status type
    // status type : The asset status["statusType"] property. Either "NORMAL", "WARNING", or "ERROR"
    // statuses_db: Dictionary array containing the database organized by status types
    getAssetIdsByStatusType: (status_type, statuses_db) => {

        // should include some error handling here if status_type is not specified right        
        return statuses_db[status_type];
    }
}