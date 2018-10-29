package com.lotus.iot.mpm.eventhandler;

import com.lotus.iot.mpm.eventhandler.datamodel.AssetStatusEventService;
import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusChange;
import com.lotus.iot.mpm.eventhandler.strategy.StatusChangeStrategy;
import com.lotus.iot.mpm.eventhandler.strategy.StatusChangeStrategyManager;
import com.lotus.iot.mpm.util.PersistentQueueMng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public class QueueMngAssetStatusChange extends PersistentQueueMng {
    private final static Logger LOGGER = LoggerFactory.getLogger(QueueMngAssetStatusChange.class);
    private AssetStatusChangeQueue assetStatusChangeQueue;
    private AssetStatusEventService assetStatusEventService;
    private StatusChangeStrategyManager statusChangeStrategyManager;

    @Autowired
    public QueueMngAssetStatusChange(AssetStatusChangeQueue assetStatusChangeQueue,
                                     AssetStatusEventService assetStatusEventService, StatusChangeStrategyManager statusChangeStrategyManager) {
        this.assetStatusChangeQueue = assetStatusChangeQueue;
        this.assetStatusEventService = assetStatusEventService;
        this.statusChangeStrategyManager = statusChangeStrategyManager;
    }

    @PostConstruct
    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    protected void doTakeFromQueue() {
        AssetStatusChange assetStatusChange = assetStatusChangeQueue.poll();
        if (assetStatusChange != null) {
            //log status change considering previous status
            LOGGER.debug("Fetched assetStatusChange {} from Queue with size: {}",
                    assetStatusChange, assetStatusChangeQueue.size());
            StatusChangeStrategy statusChangeStrategy = statusChangeStrategyManager.chooseStrategy(assetStatusChange.getNewStatus());
            statusChangeStrategy.log(assetStatusChange);
            //notify event
            assetStatusEventService.statusChange(assetStatusChange.getAssetId(), assetStatusChange.getNewStatus());
        }
    }
}
