package com.lotus.iot.mpm.api.objectmodel.response;

import com.lotus.iot.mpm.objectmodel.type.StatusType;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class AssetCurrentStatusResponse extends GeneralResponse {
    private StatusType statusType;

    public AssetCurrentStatusResponse(GeneralResponse generalResponse, StatusType statusType) {
        super(generalResponse.getStatus(), generalResponse.getMessage());
        this.statusType = statusType;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    @Override
    public String toString() {
        return "AssetCurrentStatusResponse{" +
                "responseStatus=" + super.getStatus() +
                ", responseMessage=" + super.getMessage() +
                ", statusType=" + statusType +
                '}';
    }
}
