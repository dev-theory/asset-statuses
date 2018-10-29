const EventEmitter   = require("events");
const consoleManager = require("./consoleManager.js");
const eventNames     = require("./enums/eventNames.js");

// Class to allow consumers to subscribe to asset status events and notifies them about those events
module.exports = class Consumer extends EventEmitter {        

    // name: name of consumer. Unused but thought it made sense to have    
    // In theory consumer could change their name which isn't implemented here
    constructor(name) {        
        super();
        this.name = name;  
    }

    // Alternate simple implementation which could be used if message creation is done in
    // eventManager. Doesn't allow for us to easily have events complete different actions in the future
    // subscribeToEvents(events_to_subscribe_to) {
    // events_to_subscribe_to.forEach(event => {
    //     this.on(event, (message) => {
    //         consoleManager.handle(message);                
    //     });
    // }

    // Subscribe consumer to events specified
    // events_to_subscribe_to: Array of events the consumer wants to be notified of
    subscribeToEvents(events_to_subscribe_to){
            
        events_to_subscribe_to.forEach(event => {

            if (event === eventNames.AssetMayFail) {

                this.on(eventNames.AssetMayFail, (asset_status) => {

                    const message = "Asset "  + asset_status["assetId"] + " is about to fail with status WARNING at " + asset_status["createdAt"];
                    consoleManager.handle(message);                
                });
            }

            else if (event === eventNames.AssetFailed){

                this.on(eventNames.AssetFailed, (asset_status) => {                    

                    const message = "Asset "  + asset_status["assetId"] + " has eventually failed with status ERROR " + asset_status["createdAt"];
                    consoleManager.handle(message);                
                });
            }

            else if (event === eventNames.AssetFailedAbruptly){
                
                this.on(eventNames.AssetFailedAbruptly, (asset_status) => {

                    const message = "Asset "  + asset_status["assetId"] + " has abruptly failed with status ERROR at " + asset_status["createdAt"];
                    consoleManager.handle(message);                
                });            
            }
            
            else if (event === eventNames.AssetRecovered){            
            
                this.on(eventNames.AssetRecovered, (asset_status) => {
                    
                    const message = "Asset "  + asset_status["assetId"] + " has recovered with status NORMAL at " + asset_status["createdAt"];
                    consoleManager.handle(message);
                });
            }

            else {

                throw new Error("Trying to subscribe to unknown event: " + event);
            }
        });
    }
}