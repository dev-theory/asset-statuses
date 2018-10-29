package com.lotus.iot.mpm.eventhandler;

import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public class AssetStatusChangeQueue extends LinkedBlockingQueue<AssetStatusChange> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AssetStatusChangeQueue.class);

    @Override
    public void put(AssetStatusChange assetStatusChange) throws InterruptedException {
        super.put(assetStatusChange);
        LOGGER.debug("{} added to queue, size {}", assetStatusChange, super.size());
    }

    @Override
    public AssetStatusChange poll() {
        AssetStatusChange assetStatusChange = super.poll();
        if (assetStatusChange != null) {
            LOGGER.debug("{} removed from queue, size {}", assetStatusChange, super.size());
        }
        return assetStatusChange;
    }
}
