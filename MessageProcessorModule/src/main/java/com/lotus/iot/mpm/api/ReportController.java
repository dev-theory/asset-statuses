package com.lotus.iot.mpm.api;

import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponse;
import com.lotus.iot.mpm.report.ReportHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@RestController
@RequestMapping("report")
public class ReportController extends AbstractController {
    private final ReportHandler reportHandler;

    @Autowired
    public ReportController(ReportHandler reportHandler) {
        this.reportHandler = reportHandler;
    }

    @RequestMapping(value = "/asset/{asset_id}/status", method = RequestMethod.GET)
    public ResponseEntity assetCurrentStatus(@PathVariable("asset_id") String assetId) {
        GeneralResponse response = reportHandler.assetCurrentStatus(assetId);
        return generateResponse(response);
    }

    @RequestMapping(value = "/asset/status/{status_type}/all", method = RequestMethod.GET)
    public ResponseEntity assetsInStatus(@PathVariable("status_type") String statusType) {
        GeneralResponse response = reportHandler.assetsInState(statusType);
        return generateResponse(response);
    }
}
