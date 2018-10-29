const Consumer = require("../../consumer.js");
const eventNames = require("../../enums/eventNames.js");

describe("consumer", () => {
    
    it("should do nothing if event subscriptions are correct", () => {        
        const consumer1 = new Consumer("paul");
        expect(() => {consumer1.subscribeToEvents([eventNames.AssetFailed, eventNames.AssetMayFail, eventNames.AssetRecovered])});
    });

    it("should throw an error if an incorrect event is passed in", () => {
        const consumer1 = new Consumer("paul");
        expect(() => {consumer1.subscribeToEvents(["nonExistentEvent"])}).toThrow();              
    });
});
