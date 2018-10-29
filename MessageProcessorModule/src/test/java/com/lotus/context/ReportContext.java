package com.lotus.context;

import com.lotus.iot.mpm.datamodel.service.AssetStatusService;
import com.lotus.iot.mpm.report.ReportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Configuration
@Import(DataModelContext.class)
public class ReportContext {

    @Bean
    public ReportHandler getReportHandler(AssetStatusService assetStatusService) {
        return new ReportHandler(assetStatusService);
    }
}
