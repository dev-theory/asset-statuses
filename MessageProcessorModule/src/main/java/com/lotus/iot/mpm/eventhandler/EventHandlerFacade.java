package com.lotus.iot.mpm.eventhandler;

import com.lotus.iot.mpm.api.objectmodel.request.SubscribeRequest;
import com.lotus.iot.mpm.eventhandler.datamodel.AssetStatusEventService;
import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusChange;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public class EventHandlerFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlerFacade.class);
    private AssetStatusChangeQueue assetStatusChangeQueue;
    private AssetStatusEventService assetStatusEventService;

    @Autowired
    public EventHandlerFacade(AssetStatusChangeQueue assetStatusChangeQueue, AssetStatusEventService assetStatusEventService) {
        this.assetStatusChangeQueue = assetStatusChangeQueue;
        this.assetStatusEventService = assetStatusEventService;
    }

    public void notifyStatusChange(StatusType newStatus, StatusType oldStatus, String assetId, Date updateTime) {
        AssetStatusChange assetStatusChange = new AssetStatusChange(newStatus, oldStatus, assetId, updateTime);
        try {
            assetStatusChangeQueue.put(assetStatusChange);
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException while trying to put {} in StatusChangeQueue", assetStatusChange);
        }
    }

    public void subscribe(SubscribeRequest subscribeRequest) {
        assetStatusEventService.subscribe(subscribeRequest);
    }
}
