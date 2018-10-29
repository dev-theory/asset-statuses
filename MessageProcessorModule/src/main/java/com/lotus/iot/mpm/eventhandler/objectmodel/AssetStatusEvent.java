package com.lotus.iot.mpm.eventhandler.objectmodel;

import com.lotus.iot.mpm.objectmodel.type.StatusType;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public enum AssetStatusEvent {
    AssetMayFail(StatusType.WARNING),
    AssetFailed(StatusType.ERROR),
    AssetRecovered(StatusType.NORMAL);

    private StatusType targetStatusType;

    AssetStatusEvent(StatusType targetStatusType) {
        this.targetStatusType = targetStatusType;
    }

    public StatusType getTargetStatus() {
        return this.targetStatusType;
    }
}
