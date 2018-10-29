package com.lotus.iot.mpm.eventhandler.strategy;

import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public class StatusChangeStrategyManager {
    private Map<StatusType, StatusChangeStrategy> strategyMap;

    @Autowired
    public StatusChangeStrategyManager(ErrorStatusChangeStrategy errorStatusChangeLogStrategy, OtherStatusChangeStrategy otherStatusChangeStrategy) {
        this.strategyMap = new HashMap<>();
        this.strategyMap.put(StatusType.ERROR, errorStatusChangeLogStrategy);
        this.strategyMap.put(StatusType.WARNING, otherStatusChangeStrategy);
        this.strategyMap.put(StatusType.NORMAL, otherStatusChangeStrategy);
    }

    public StatusChangeStrategy chooseStrategy(StatusType statusType) {
        return this.strategyMap.get(statusType);
    }
}
