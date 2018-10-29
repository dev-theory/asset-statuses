package com.lotus.iot.mpm.report;

import com.lotus.iot.mpm.api.objectmodel.response.AllAssetsInStateResponse;
import com.lotus.iot.mpm.api.objectmodel.response.AssetCurrentStatusResponse;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponse;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponseCode;
import com.lotus.iot.mpm.datamodel.service.AssetStatusService;
import com.lotus.iot.mpm.exception.ValidationException;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import com.lotus.iot.mpm.validation.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public class ReportHandler {
    private AssetStatusService assetStatusService;

    @Autowired
    public ReportHandler(AssetStatusService assetStatusService) {
        this.assetStatusService = assetStatusService;
    }

    public GeneralResponse assetCurrentStatus(String assetId) {
        StatusType statusType = assetStatusService.getCurrentStatus(assetId);
        return statusType != null ?
                new AssetCurrentStatusResponse(GeneralResponseCode.SUCCESS, statusType) :
                GeneralResponseCode.NO_ASSET_RECORD_FOUND;
    }

    public GeneralResponse assetsInState(String inputStatus) {
        try {
            //validate
            StatusType statusType = ValidationHandler.statusType(inputStatus);

            //ger report
            List<String> assetIdList = assetStatusService.loadAllAssetsInState(statusType);
            return new AllAssetsInStateResponse(GeneralResponseCode.SUCCESS, assetIdList);
        } catch (ValidationException e) {
            return e.getResponse();
        }
    }
}
