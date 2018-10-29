package com.lotus.iot.mpm.datamodel.service;

import com.lotus.context.DataModelContext;
import com.lotus.iot.mpm.datamodel.service.objectmodel.Status;
import com.lotus.iot.mpm.exception.OldMessageException;
import com.lotus.iot.mpm.objectmodel.AssetStatusMessage;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataModelContext.class)
public class AssetStatusServiceTest {

    @Autowired
    private AssetStatusService assetStatusService;

    private AssetStatusMessage newAssetStatus;
    private AssetStatusMessage oldAssetStatus;

    @Before
    public void setup() {
        Date oldCreatedAt = new Date(1494039905000L);
        Date createdAt = new Date();

        String assetId = UUID.randomUUID().toString();
        newAssetStatus = new AssetStatusMessage(assetId, StatusType.NORMAL, createdAt);

        oldAssetStatus = new AssetStatusMessage(assetId, StatusType.ERROR, oldCreatedAt);
    }

    @Test
    public void updateAssetStatus() throws OldMessageException {
        AssetStatusMessage assetStatus = new AssetStatusMessage(UUID.randomUUID().toString(), StatusType.ERROR, new Date());
        assetStatusService.updateStatus(assetStatus);
        assert (assetStatus.getStatusType().equals(assetStatusService.getCurrentStatus(assetStatus.getAssetId())));
    }

    @Test(expected = OldMessageException.class)
    public void updateWithOldStatus() throws OldMessageException {
        assetStatusService.updateStatus(newAssetStatus);
        assetStatusService.updateStatus(oldAssetStatus);
    }

    @Test
    public void loadAllAssetsInState() throws OldMessageException {
        StatusType statusType = StatusType.ERROR;
        AssetStatusMessage assetStatus = new AssetStatusMessage(UUID.randomUUID().toString(), statusType, new Date());
        assetStatusService.updateStatus(assetStatus);
        List<String> assetIdList = assetStatusService.loadAllAssetsInState(statusType);
        assert (assetIdList.size() > 0);
    }

    @Test
    public void assetStatusToString() throws OldMessageException {
        Status status = new Status(StatusType.WARNING,new Date());
        status.toString();
    }
}
