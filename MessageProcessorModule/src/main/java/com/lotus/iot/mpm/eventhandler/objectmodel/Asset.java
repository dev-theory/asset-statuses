package com.lotus.iot.mpm.eventhandler.objectmodel;

import com.lotus.iot.mpm.objectmodel.Observable;
import com.lotus.iot.mpm.objectmodel.Observer;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class Asset implements Observable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Asset.class);
    private Map<StatusType, List<Observer>> eventObserverListMap;
    private String assetId;
    private StatusType status;

    public Asset(String assetId) {
        this.assetId = assetId;
        eventObserverListMap = new HashMap<>();
        eventObserverListMap.put(StatusType.WARNING, new ArrayList<>());
        eventObserverListMap.put(StatusType.ERROR, new ArrayList<>());
        eventObserverListMap.put(StatusType.NORMAL, new ArrayList<>());
    }

    public void addObserver(AssetStatusEvent event, Observer listener) {
        eventObserverListMap.get(event.getTargetStatus()).add(listener);
        LOGGER.info("Subscribed listener to event {} of asset '{}'", event, assetId);
    }

    public void removeObserver(AssetStatusEvent event, Observer listener) {
        eventObserverListMap.get(event.getTargetStatus()).remove(listener);
    }

    @Override
    public void notifyObserver() {
        List<Observer> observerList = eventObserverListMap.get(status);
        for (Observer listener : observerList) {
            listener.update(this);
        }
    }

    public String getAssetId() {
        return assetId;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
        notifyObserver();
    }
}
