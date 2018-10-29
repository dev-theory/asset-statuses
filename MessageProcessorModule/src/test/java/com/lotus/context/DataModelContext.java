package com.lotus.context;

import com.lotus.iot.mpm.datamodel.service.AssetStatusMap;
import com.lotus.iot.mpm.datamodel.service.AssetStatusService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Configuration
public class DataModelContext {

    @Bean
    public AssetStatusService getAssetStatusService() {
        AssetStatusMap assetStatusMap = new AssetStatusMap();
        return new AssetStatusService(assetStatusMap);
    }
}
