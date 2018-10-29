package com.lotus.iot.mpm.report;

import com.lotus.context.ReportContext;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponse;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponseCode;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReportContext.class)
public class ReportTest {

    @Autowired
    ReportHandler reportHandler;

    @Test
    public void getCurrentStatus() {
        GeneralResponse response = reportHandler.assetCurrentStatus(UUID.randomUUID().toString());
        assert (response == GeneralResponseCode.NO_ASSET_RECORD_FOUND);
    }

    @Test
    public void getAllAssetsInState() {
        GeneralResponse response = reportHandler.assetsInState(StatusType.WARNING.toString());
        assert (response.getStatus().equals(GeneralResponseCode.SUCCESS.getStatus()));
    }

    @Test
    public void invalidStatusType() {
        GeneralResponse response = reportHandler.assetsInState("warn");
        assert (response == GeneralResponseCode.INVALID_STATUS_TYPE);
    }
}
