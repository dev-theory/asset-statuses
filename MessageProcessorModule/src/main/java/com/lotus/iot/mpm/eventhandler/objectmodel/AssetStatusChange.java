package com.lotus.iot.mpm.eventhandler.objectmodel;

import com.lotus.iot.mpm.objectmodel.type.StatusType;

import java.util.Date;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class AssetStatusChange {
    private StatusType newStatus;
    private StatusType oldStatus;
    private String assetId;
    private Date updateTime;

    public AssetStatusChange(StatusType newStatus, StatusType oldStatus, String assetId, Date updateTime) {
        this.newStatus = newStatus;
        this.oldStatus = oldStatus;
        this.assetId = assetId;
        this.updateTime = updateTime;
    }

    public StatusType getNewStatus() {
        return newStatus;
    }

    public StatusType getOldStatus() {
        return oldStatus;
    }

    public String getAssetId() {
        return assetId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String toString() {
        return "AssetStatusChange{" +
                "newStatus=" + newStatus +
                ", oldStatus=" + oldStatus +
                ", assetId='" + assetId + '\'' +
                '}';
    }
}
