// "statusType" property of asset status objects
// Cannot use symbol here as it fails to compare to the string type of asset status["statusType"]
module.exports = Object.freeze({
    "NORMAL":"NORMAL",
    "WARNING":"WARNING",
    "ERROR":"ERROR"});