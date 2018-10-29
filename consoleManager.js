module.exports = {    

    // Prints array of asset ids with their status type to the screen
    // asset_statuses: Array of assets_statuses to print
    logDifferentAssetIdAndStatusType: (asset_statuses) => {
        
        let message = "";
        asset_statuses.forEach(asset_status => {

            // If the asset wasn't found
            if (asset_status === null){        

                message = message + "\nAsset '" + asset_status + "' not found in database";
            }
            
            // If it was found
            else{

                message = message + "\nCurrent status for asset '" + asset_status["assetId"] + "' is '" +
                asset_status["statusType"] + "'";                
            }   
        });                 
        module.exports.handle(message);
    },

    // Prints array of asset ids to the screen, given their common statusType
    // asset_statuses: Array of assets_statuses to print
    logAssetIdsGivenStatusType: (asset_statuses) => {                    
        
        let message = "";     
        
        if (asset_statuses === undefined) {

            message = "\nNo assets found for specified status type";
        }

        else {
            
            message = "\nThere are " + asset_statuses.length + " assets which currently have an " 
            + asset_statuses[0]["statusType"] + " status";
            
            asset_statuses.forEach(asset_status => {

                // If it's the last asset being output, do not append a comma
                if (asset_status === asset_statuses[asset_statuses.length - 1]){
                    message = message + "\n'" + asset_status["assetId"] + "'";
                }

                // append a comma if it is not the last message
                else{
                    message = message + "\n'" + asset_status["assetId"] + "',";
                }     
            });
        }    
        module.exports.handle(message);        
    },

    // handle message processing
    // message: message to be handled. Currently is only logged to stdout
    handle(message){
        console.log(message);
    }
}