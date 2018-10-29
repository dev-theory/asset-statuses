package com.lotus.iot.mpm.eventhandler.datamodel;

import com.lotus.iot.mpm.api.objectmodel.request.SubscribeRequest;
import com.lotus.iot.mpm.eventhandler.objectmodel.Asset;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Service
public class AssetStatusEventService {
    private AssetStatusEventMap assetStatusEventMap;

    @Autowired
    public AssetStatusEventService(AssetStatusEventMap assetStatusEventMap) {
        this.assetStatusEventMap = assetStatusEventMap;
    }

    public void subscribe(SubscribeRequest subscribeRequest) {
        String assetId = subscribeRequest.getAssetId();
        Asset asset = assetStatusEventMap.get(assetId);

        if (asset == null) {
            asset = new Asset(assetId);
        }
        asset.addObserver(subscribeRequest.getStatusEvent(), subscribeRequest.getObserver());
        assetStatusEventMap.put(asset.getAssetId(), asset);
    }

    public void statusChange(String assetId, StatusType newStatus) {
        Asset asset = assetStatusEventMap.get(assetId);
        if (asset != null) {
            asset.setStatus(newStatus);
        }
    }
}
