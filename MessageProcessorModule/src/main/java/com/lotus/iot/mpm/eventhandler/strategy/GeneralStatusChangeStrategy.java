package com.lotus.iot.mpm.eventhandler.strategy;

import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusChange;
import org.springframework.stereotype.Component;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public class GeneralStatusChangeStrategy extends AbstractStatusChangeStrategy {

    @Override
    String generateErrorSpecificMessage(AssetStatusChange assetStatusChange) {
        return assetStatusChange.getNewStatus().getDescription();
    }
}
