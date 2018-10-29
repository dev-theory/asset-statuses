package com.lotus.context;

import com.lotus.iot.mpm.eventhandler.AssetStatusChangeQueue;
import com.lotus.iot.mpm.eventhandler.EventHandlerFacade;
import com.lotus.iot.mpm.eventhandler.datamodel.AssetStatusEventMap;
import com.lotus.iot.mpm.eventhandler.datamodel.AssetStatusEventService;
import com.lotus.iot.mpm.eventhandler.strategy.ErrorStatusChangeStrategy;
import com.lotus.iot.mpm.eventhandler.strategy.OtherStatusChangeStrategy;
import com.lotus.iot.mpm.eventhandler.strategy.StatusChangeStrategyManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Configuration
public class EventHandlerContext {

    @Bean
    public AssetStatusEventService getAssetStatusEventService() {
        AssetStatusEventMap eventMap = new AssetStatusEventMap();
        return new AssetStatusEventService(eventMap);
    }

    @Bean
    public EventHandlerFacade getEventHandlerFacade(AssetStatusEventService assetStatusEventService) {
        AssetStatusChangeQueue queue = new AssetStatusChangeQueue();
        return new EventHandlerFacade(queue, assetStatusEventService);
    }

    @Bean
    public StatusChangeStrategyManager getStatusChangeStrategyManager() {
        OtherStatusChangeStrategy otherStatusChangeStrategy = new OtherStatusChangeStrategy();
        ErrorStatusChangeStrategy errorStrategy = new ErrorStatusChangeStrategy();
        return new StatusChangeStrategyManager(errorStrategy, otherStatusChangeStrategy);

    }
}
