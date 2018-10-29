package com.lotus.iot.mpm.handler;

import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponse;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponseCode;
import com.lotus.iot.mpm.datamodel.service.AssetStatusService;
import com.lotus.iot.mpm.eventhandler.EventHandlerFacade;
import com.lotus.iot.mpm.exception.OldMessageException;
import com.lotus.iot.mpm.objectmodel.AssetStatusMessage;
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
public class MessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

    private AssetStatusService assetStatusService;
    private EventHandlerFacade eventHandlerFacade;

    @Autowired
    public MessageHandler(AssetStatusService assetStatusService, EventHandlerFacade eventHandlerFacade) {
        this.assetStatusService = assetStatusService;
        this.eventHandlerFacade = eventHandlerFacade;
    }

    public GeneralResponse assetStatus(AssetStatusMessage assetStatusMessage) {
        try {
            //store asset status
            StatusType oldStatusType = assetStatusService.updateStatus(assetStatusMessage);
            StatusType newStatusType = assetStatusMessage.getStatusType();

            //notify if status changed
            if (oldStatusType != newStatusType) {
                String assetId = assetStatusMessage.getAssetId();
                Date updateTime = assetStatusMessage.getCreatedAt();

                eventHandlerFacade.notifyStatusChange(newStatusType, oldStatusType, assetId, updateTime);
            }
            return GeneralResponseCode.SUCCESS;
        } catch (OldMessageException e) {
            LOGGER.error("Old status message received for asset with id {}.",assetStatusMessage.getAssetId());
            return e.getResponse();
        }
    }
}
