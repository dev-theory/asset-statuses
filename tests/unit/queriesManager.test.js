const queriesManager = require("../../queriesManager.js");
const statusTypes    = require("../../enums/statusTypes.js");

const asset_status_1 = {"id": 1, "assetId": 1, "statusType": statusTypes.NORMAL,  "createdAt": "Time 1"}
const asset_status_2 = {"id": 2, "assetId": 2, "statusType": statusTypes.NORMAL,  "createdAt": "Time 2"}
const asset_status_3 = {"id": 3, "assetId": 3, "statusType": statusTypes.WARNING, "createdAt": "Time 3"}
const ids_db = [asset_status_1, asset_status_2]
const statuses_db = {[statusTypes.NORMAL]: [asset_status_1, asset_status_2], [statusTypes.WARNING]: [asset_status_3], [statusTypes.ERROR]: []}

describe("queriesManager", () => {        

    it("getAssetStatusesByAssetIDs should return assets when given array of asset ids", () => {        

        const latest_statuses = queriesManager.getAssetStatusesByAssetIDs([1, 2], ids_db);
        expect(latest_statuses).toEqual([asset_status_1, asset_status_2]);
    });

    it("getAssetStatusesByAssetIDs should return null if asset doesn't exist", () => {

        const latest_statuses = queriesManager.getAssetStatusesByAssetIDs([3], ids_db);
        expect(latest_statuses).toEqual([{"id": null, "assetId": 3, "statusType": null, "createdAt": null}]);
    });

    it("getAssetIdsByStatusType should return all assets with specified status", () => {
                
        const all_assets_with_given_status = queriesManager.getAssetIdsByStatusType(statusTypes.NORMAL, statuses_db);
        expect(all_assets_with_given_status).toEqual([asset_status_1, asset_status_2]);
    });    

    it("getAssetIdsByStatusType should return empty array if no asset with that status exist", () => {

        const all_assets_with_given_status = queriesManager.getAssetIdsByStatusType(statusTypes.ERROR, statuses_db);        
        expect(all_assets_with_given_status).toEqual([]);
    });

    it("getAssetIdsByStatusType should return undefined if status is entered incorrectly", () => {

        const all_assets_with_given_status = queriesManager.getAssetIdsByStatusType("nonExistantStatus", statuses_db);        
        expect(all_assets_with_given_status).toEqual(undefined);
    });
});