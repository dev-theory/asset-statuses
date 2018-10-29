package com.lotus.iot.mpm.eventhandler.strategy;

import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusChange;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public abstract class AbstractStatusChangeStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStatusChangeStrategy.class);

    public void log(AssetStatusChange assetStatusChange) {
        String ErrorSpecificMessage = generateErrorSpecificMessage(assetStatusChange);
        String assetId = assetStatusChange.getAssetId();
        StatusType newStatus = assetStatusChange.getNewStatus();
        Date statusUpdateTime = assetStatusChange.getUpdateTime();

        LOGGER.info("Asset {} {} with status {} at {}", assetId, ErrorSpecificMessage, newStatus, statusUpdateTime);
    }

    abstract String generateErrorSpecificMessage(AssetStatusChange assetStatusChange);
}
