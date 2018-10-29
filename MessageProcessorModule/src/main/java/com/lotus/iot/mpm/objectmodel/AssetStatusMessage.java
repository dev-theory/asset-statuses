package com.lotus.iot.mpm.objectmodel;

import com.lotus.iot.mpm.objectmodel.type.StatusType;

import java.util.Date;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class AssetStatusMessage extends Message {
    private String assetId;

    private StatusType statusType;

    private Date createdAt;

    public AssetStatusMessage() {
    }

    public AssetStatusMessage(String assetId, StatusType statusType, Date createdAt) {
        this.assetId = assetId;
        this.statusType = statusType;
        this.createdAt = createdAt;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AssetStatusMessage{" +
                "id='" + super.getId() + '\'' +
                ", assetId='" + this.assetId + '\'' +
                ", statusType=" + this.statusType +
                ", createdAt='" + this.createdAt + '\'' +
                '}';
    }
}
