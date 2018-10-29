package com.lotus.iot.mpm.handler;

import com.lotus.context.HandlerContext;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponse;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponseCode;
import com.lotus.iot.mpm.objectmodel.AssetStatusMessage;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HandlerContext.class)
public class MessageHandlerTest {

    @Autowired
    private MessageHandler messageHandler;

    @Test
    public void handleAssetStatusMessage() {
        AssetStatusMessage statusMessage = new AssetStatusMessage(UUID.randomUUID().toString(), StatusType.WARNING, new Date());
        GeneralResponse response = messageHandler.assetStatus(statusMessage);
        assert (GeneralResponseCode.SUCCESS.getStatus().equals(response.getStatus()));
    }

    @Test
    public void passOldMessage() {
        AssetStatusMessage statusMessage = new AssetStatusMessage(UUID.randomUUID().toString(), StatusType.WARNING, new Date());
        messageHandler.assetStatus(statusMessage);
        statusMessage.setCreatedAt(new Date(1494039905000L));
        GeneralResponse response = messageHandler.assetStatus(statusMessage);
        assert (GeneralResponseCode.OUT_DATED == response);
    }

}
