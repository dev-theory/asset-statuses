package com.lotus.iot.mpm.datamodel.service;

import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponseCode;
import com.lotus.iot.mpm.datamodel.service.objectmodel.Status;
import com.lotus.iot.mpm.exception.OldMessageException;
import com.lotus.iot.mpm.objectmodel.AssetStatusMessage;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Service
public class AssetStatusService {
    private AssetStatusMap assetStatusMap;

    @Autowired
    public AssetStatusService(AssetStatusMap assetStatusMap) {
        this.assetStatusMap = assetStatusMap;
    }

    public StatusType updateStatus(AssetStatusMessage assetStatusMessage) throws OldMessageException {
        String assetId = assetStatusMessage.getAssetId();
        StatusType newStatusType = assetStatusMessage.getStatusType();
        Date updatedAt = assetStatusMessage.getCreatedAt();
        Status currentStatus = assetStatusMap.get(assetId);
        //updateStatus asset status in map if it's a new status
        if (currentStatus == null || updatedAt.getTime() >= currentStatus.getCreatedAt()) {
            assetStatusMap.put(assetId, newStatusType, updatedAt);
            //set old asset status as NORMAL if tere is no previous record
            return currentStatus == null ? StatusType.NORMAL : currentStatus.getStatusType();
        }
        //if new status creation time was older than existing record
        throw new OldMessageException(GeneralResponseCode.OUT_DATED);
    }

    public StatusType getCurrentStatus(String assetId) {
        Status currentStatus = assetStatusMap.get(assetId);
        return currentStatus != null ? currentStatus.getStatusType() : null;
    }

    public List<String> loadAllAssetsInState(StatusType statusType) {
        ArrayList<String> assetIdList = assetStatusMap.filterByState(statusType);
        return assetIdList != null ? assetIdList : new ArrayList<>();
    }
}
