module.exports = {    

    // Updates the database containing the array of unique asset statuses with their latest status
    // ids_db: the database of asset statuses
    // new_asset_status: the asset status we want to either add to the database, or update asset status in database
    // returns the updated ids_db and the statusType of the asset that was in the database if it existed, returns null if it doesn't exist

    // May be better to only store the assetId in this database. Stored entire asset status, in case
    // in the future we want to have additional queries to retrieve other information
    updateIDsDB: (ids_db, new_asset_status) => {
        
        // loop through the ids_db and find the index where the assetIds property matches
        // Returns -1 if not found
        const index = ids_db.findIndex(asset_in_db => asset_in_db["assetId"] === new_asset_status["assetId"]);

        let old_status = null;

        // If it does not exist inside the database, add to database
        if (index === -1){
            
            ids_db.push(new_asset_status);            
        }

        // If it does exist inside the database, update that element
        else{
                        
            old_status = ids_db[index]["statusType"];
            ids_db[index] = new_asset_status;            
        }    

        return ids_db, old_status;
    },

    // Updates the database containing arrays which only contain asset statuses of the same type
    // (NORMAL, WARNING, and ERROR are separate arrays)
    // statuses_db: the database separated by asset statuses
    // new_asset_status: the asset status we want to either add to the database, or update asset status in database
    // returns the updated statuses_db
    updateStatusesDB: (statuses_db, new_asset_status) => {        

        // Loop through dictionary of arrays
        let index;
        for (const [status_type, _] of Object.entries(statuses_db)) {
            
            // Find if the new asset already exists in one of the status databases
            index = statuses_db[status_type].findIndex(asset => asset["assetId"] == new_asset_status["assetId"]);

            // If it exists inside the database
            if (index !== -1) {

                // Note: code is simpler to just delete the asset if it exists and push to the end
                // Chose to update the index as if it were a real database to better represent a real scenario
                // statuses_db[status_type].splice(index, 1); // 
                // break;
                //}
                //statuses_db[new_asset_status["statusType"]].push(new_asset_status);

                // If the status type is the same, just want to update that element, instead of deleting and pushing to end
                if (status_type === new_asset_status["statusType"]) {

                    statuses_db[status_type][index] = new_asset_status;                    
                }

                // If the status type has changed, delete that element and push to end of new status type
                else {

                    statuses_db[status_type].splice(index, 1);
                    statuses_db[new_asset_status["statusType"]].push(new_asset_status);
                }

                break; // Will only exist in one of the status types
            }
        }

        // If the object was not found inside the database, index will be negative 1
        if (index === -1) {

            statuses_db[new_asset_status["statusType"]].push(new_asset_status);
        }
        
        return statuses_db;
    },

    // Both databases should be updated at the same time, so we do not have to record the old status in both
    // ids_db: the database of asset statuses
    // statuses_db: the database separated by asset statuses
    // new_asset_status: the asset status we want to either add to the database, or update asset status in database
    // returns updated ids_db, statuses_db, and the statusType of the asset that was in the database if it
    // existed, returns null if it doesn't exist
    updateDatabases: (ids_db, statuses_db, new_asset_status) => {

        ids_db, old_status = module.exports.updateIDsDB(ids_db, new_asset_status);
        statuses_db = module.exports.updateStatusesDB(statuses_db, new_asset_status);
        return ids_db, statuses_db, old_status;
    }
}