package com.lotus.iot.mpm.api.objectmodel.response;

import java.util.List;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class AllAssetsInStateResponse extends GeneralResponse {
    private List<String> assetIdList;

    public AllAssetsInStateResponse(GeneralResponse success, List<String> assetIdList) {
        super(success.getStatus(), success.getMessage());
        this.assetIdList = assetIdList;
    }

    public List<String> getAssetIdList() {
        return assetIdList;
    }

    @Override
    public String toString() {
        return "AssetCurrentStatusResponse{" +
                "responseStatus=" + super.getStatus() +
                ", responseMessage=" + super.getMessage() +
                ", assetIdList=" + assetIdList +
                '}';
    }
}
