package com.lotus.iot.mpm.api.objectmodel.request;

import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusEvent;
import com.lotus.iot.mpm.eventhandler.objectmodel.SampleListener;
import com.lotus.iot.mpm.objectmodel.Observer;


/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class SubscribeRequest {
    private String assetId;
    private AssetStatusEvent statusEvent;
    private SampleListener observer;

    public SubscribeRequest() {
    }

    public SubscribeRequest(String assetId, AssetStatusEvent statusEvent, SampleListener observer) {
        this.assetId = assetId;
        this.statusEvent = statusEvent;
        this.observer = observer;
    }

    public String getAssetId() {
        return assetId;
    }

    public AssetStatusEvent getStatusEvent() {
        return statusEvent;
    }

    public Observer getObserver() {
        return observer;
    }
}
