// ES6 version of enums
// Contains all possible events that can be raised
module.exports = Object.freeze({    
    AssetMayFail:        Symbol("AssetMayFail"),
    AssetFailed:         Symbol("AssetFailed"),
    AssetFailedAbruptly: Symbol("AssetFailedAbruptly"),
    AssetRecovered:      Symbol("AssetRecovered")});