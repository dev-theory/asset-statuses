package com.lotus.iot.mpm.eventhandler.objectmodel;

import com.lotus.iot.mpm.objectmodel.Observable;
import com.lotus.iot.mpm.objectmodel.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class SampleListener implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleListener.class);

    public SampleListener() {
    }

    @Override
    public void update(Observable o) {
        LOGGER.info("A listener was notified about this event");
    }
}
