package com.lotus.context;

import com.lotus.iot.mpm.datamodel.service.AssetStatusService;
import com.lotus.iot.mpm.eventhandler.EventHandlerFacade;
import com.lotus.iot.mpm.handler.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Configuration
@Import({DataModelContext.class, EventHandlerContext.class})
public class HandlerContext {

    @Bean
    public MessageHandler getMessageHandler(AssetStatusService assetStatusService, EventHandlerFacade eventHandlerFacade) {
        return new MessageHandler(assetStatusService, eventHandlerFacade);
    }

}
