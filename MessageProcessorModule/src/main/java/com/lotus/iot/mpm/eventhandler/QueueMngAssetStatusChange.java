package com.lotus.iot.mpm.eventhandler;

import com.lotus.iot.mpm.eventhandler.datamodel.AssetStatusEventService;
import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusChange;
import com.lotus.iot.mpm.eventhandler.strategy.AbstractStatusChangeStrategy;
import com.lotus.iot.mpm.eventhandler.strategy.ErrorStatusChangeStrategy;
import com.lotus.iot.mpm.eventhandler.strategy.GeneralStatusChangeStrategy;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import com.lotus.iot.mpm.util.PersistentQueueMng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public class QueueMngAssetStatusChange extends PersistentQueueMng {
    private final static Logger LOGGER = LoggerFactory.getLogger(QueueMngAssetStatusChange.class);
    private AssetStatusChangeQueue assetStatusChangeQueue;
    private Map<StatusType, AbstractStatusChangeStrategy> statusChangeStartegyMap;
    private AssetStatusEventService assetStatusEventService;

    @Autowired
    public QueueMngAssetStatusChange(AssetStatusChangeQueue assetStatusChangeQueue,
                                     ErrorStatusChangeStrategy errorStatusChangeLogStrategy,
                                     GeneralStatusChangeStrategy generalStatusChangeStrategy,
                                     AssetStatusEventService assetStatusEventService) {
        this.assetStatusChangeQueue = assetStatusChangeQueue;
        this.assetStatusEventService = assetStatusEventService;
        this.statusChangeStartegyMap = new HashMap<>();
        this.statusChangeStartegyMap.put(StatusType.ERROR, errorStatusChangeLogStrategy);
        this.statusChangeStartegyMap.put(StatusType.WARNING, generalStatusChangeStrategy);
        this.statusChangeStartegyMap.put(StatusType.NORMAL, generalStatusChangeStrategy);
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
            AbstractStatusChangeStrategy statusChangeStrategy = this.statusChangeStartegyMap.get(assetStatusChange.getNewStatus());
            statusChangeStrategy.log(assetStatusChange);
            //notify event
            assetStatusEventService.statusChange(assetStatusChange.getAssetId(), assetStatusChange.getNewStatus());
        }
    }
}
