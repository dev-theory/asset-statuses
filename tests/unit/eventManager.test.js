const eventManager = require("../../eventManager.js");
const eventNames   = require("../../enums/eventNames.js");
const statusTypes  = require("../../enums/statusTypes.js");

// If more statusTypes are added, should simplify tests to not have to keep adding so many
// Kept verbose to be explicit
// Go through every combination of null, NORMAL, WARNING, ERROR and check that the output is correct
describe("event manager", () => {        
            
    it("should return event = null when old status is null and new status is 'NORMAL'", () => {
        asset_status = {"assetId": "1", "statusType": statusTypes.NORMAL,"createdAt": "Time 1"};
        const event = eventManager(null, asset_status, eventNames, statusTypes);
        expect(event).toEqual(null);
    });

    it("should return event = null when old status is 'NORMAL' and new status is 'NORMAL'", () => {        
        const event = eventManager(statusTypes.NORMAL, asset_status, eventNames, statusTypes);
        expect(event).toEqual(null);
    });

    it("should return event = 'AssetRecovered' when old status is 'WARNING' and new status is 'NORMAL'", () => {                
        const event = eventManager(statusTypes.WARNING, asset_status, eventNames, statusTypes);
        expect(event).toEqual(eventNames.AssetRecovered);
    });

    it("should return event = 'AssetRecovered' old status is 'ERROR' and new status is 'NORMAL'", () => {                
        const event = eventManager(statusTypes.ERROR, asset_status, eventNames, statusTypes);
        expect(event).toEqual(eventNames.AssetRecovered);        
    });

    it("should return event = 'AssetMayFail' when old status is null and new status is 'WARNING'", () => {                
        asset_status = {"assetId": "1", "statusType": "WARNING", "createdAt": "Time 1"};
        const event = eventManager(null, asset_status, eventNames, statusTypes);
        expect(event).toEqual(eventNames.AssetMayFail);                                
    });

    it("should return event = 'AssetMayFail' when old status is 'NORMAL' and new status is 'WARNING'", () => {                
        const event = eventManager(statusTypes.NORMAL, asset_status, eventNames, statusTypes);
        expect(event).toEqual(eventNames.AssetMayFail);
    });

    it("should return event = null old status is 'WARNING' and new status is 'WARNING'", () => {        
        const event = eventManager(statusTypes.WARNING, asset_status, eventNames, statusTypes);
        expect(event).toEqual(null);
    });

    it("should return event = 'AssetMayFail' when old status is 'ERROR' and new status is 'WARNING'", () => {                
        const event = eventManager(statusTypes.ERROR, asset_status, eventNames, statusTypes);
        expect(event).toEqual(eventNames.AssetMayFail);                                
    });
    
    it("should return event = 'AssetFailedAbruptly' when old status is null and new status is 'ERROR'", () => {                        
        asset_status = {"assetId": "1","statusType": "ERROR","createdAt": "Time 1"};
        const event = eventManager(null, asset_status, eventNames, statusTypes);
        expect(event).toEqual(eventNames.AssetFailedAbruptly);
    });

    it("should return event = 'AssetFailedAbruptly' with old status is NORMAL and new status is 'ERROR'", () => {                
        const event = eventManager(statusTypes.NORMAL, asset_status, eventNames, statusTypes);
        expect(event).toEqual(eventNames.AssetFailedAbruptly);                                                        
    });

    it("should return event = 'AssetFailed' when old status is WARNING and new status is 'ERROR'", () => {                
        const event = eventManager(statusTypes.WARNING, asset_status, eventNames, statusTypes);
        expect(event).toEqual(eventNames.AssetFailed);                                                        
    });

    it("should return event = null when old status is 'ERROR' and new status is 'ERROR'", () => {        
        const event = eventManager(statusTypes.ERROR, asset_status, eventNames, statusTypes);
        expect(event).toEqual(null);
    });
});