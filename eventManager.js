// Decides what event should be emitted
// old_status: the statusType of the asset already in the database, is null if the asset has just been added to the database
// new_asset_status: contains the asset status we just added to the database
// eventNames: enum containing all the possible events
// statusTypes: enum containing all the possible types of asset["statusTypes"]
// returns: event we want to raise
module.exports = function eventManager(old_status, new_asset_status, eventNames, statusTypes){
    
    // Should add error handling if any input parameters are strange

    const new_status = new_asset_status["statusType"]; // for readability
    let event = null; // want to return null event if none of the events are triggered

    // If new status is normal and the old status is null or not normal    
    if ((old_status !== statusTypes.NORMAL && new_status === statusTypes.NORMAL) && (old_status !== null && new_status === statusTypes.NORMAL)){
                
        event = eventNames.AssetRecovered;             
    }                    

    // If the status has changed to warning
    else if (old_status !== statusTypes.WARNING && new_status == statusTypes.WARNING) {
        
        event = eventNames.AssetMayFail;           
    }
    
    // If the status has changed from warning to error
    else if (old_status == statusTypes.WARNING && new_status === statusTypes.ERROR) {

        event = eventNames.AssetFailed;
    }                
            
    // If the old status doesn't exist (is null), or status moved from normal to error
    else if ((old_status === statusTypes.NORMAL || old_status === null) && new_status === statusTypes.ERROR) {

        event = eventNames.AssetFailedAbruptly;
    }   

    return event;
}