package com.lotus.iot.mpm.eventhandler.strategy;

import com.lotus.context.EventHandlerContext;
import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusChange;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EventHandlerContext.class)
public class StatusChangeStrategyTest {

    @Autowired
    StatusChangeStrategyManager strategyManager;

    @Test
    public void abruptlyFailed() {
        StatusType newStatus = StatusType.ERROR;
        StatusType oldStatus = StatusType.NORMAL;
        String message = getMessage(newStatus, oldStatus);
        assert ("has abruptly failed".equals(message));
    }

    @Test
    public void eventuallyFailed() {
        StatusType newStatus = StatusType.ERROR;
        StatusType oldStatus = StatusType.WARNING;
        String message = getMessage(newStatus, oldStatus);
        assert ("has eventually failed".equals(message));
    }

    @Test
    public void aboutToFail() {
        StatusType newStatus = StatusType.WARNING;
        StatusType oldStatus = StatusType.NORMAL;
        String message = getMessage(newStatus, oldStatus);
        assert (newStatus.getDescription().equals(message));
    }

    @Test
    public void logMessage() {
        StatusType newStatus = StatusType.WARNING;
        StatusType oldStatus = StatusType.NORMAL;
        String assetId = UUID.randomUUID().toString();
        AssetStatusChange statusChange = new AssetStatusChange(newStatus, oldStatus, assetId, new Date());
        StatusChangeStrategy strategy = strategyManager.chooseStrategy(newStatus);
        strategy.log(statusChange);
    }

    public String getMessage(StatusType newStatus, StatusType oldStatus) {
        String assetId = UUID.randomUUID().toString();
        AssetStatusChange statusChange = new AssetStatusChange(newStatus, oldStatus, assetId, new Date());
        StatusChangeStrategy strategy = strategyManager.chooseStrategy(newStatus);
        return strategy.generateErrorSpecificMessage(statusChange);
    }
}
