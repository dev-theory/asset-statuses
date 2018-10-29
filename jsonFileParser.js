const fs = require("fs");

// Takes a JSON file as input, and returns the content as objects
// file_to_parse: filename of .json file
module.exports = (file_to_parse) => {  
    const content = fs.readFileSync(file_to_parse);
    return JSON.parse(content);;
}