package com.lotus.iot.mpm.eventhandler.strategy;

import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusChange;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public class ErrorStatusChangeStrategy extends AbstractStatusChangeStrategy {
    private Map<StatusType, String> messageBasedOnPreviousStateMap;

    public ErrorStatusChangeStrategy() {
        this.messageBasedOnPreviousStateMap = new EnumMap<>(StatusType.class);
        this.messageBasedOnPreviousStateMap.put(StatusType.WARNING, "eventually");
        this.messageBasedOnPreviousStateMap.put(StatusType.NORMAL, "abruptly");
    }

    @Override
    String generateErrorSpecificMessage(AssetStatusChange assetStatusChange) {
        StatusType oldStatus = assetStatusChange.getOldStatus();
        return String.format("has %s failed", this.messageBasedOnPreviousStateMap.get(oldStatus));
    }
}
