package com.lotus.iot.mpm.util;

import org.springframework.stereotype.Component;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Component
public abstract class PersistentQueueMng extends Thread {
    private Boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning) {
            doTakeFromQueue();
        }
    }

    protected abstract void doTakeFromQueue();

    public void shutdown() {
        this.isRunning = false;
        //for later further implement. ex. component recovery
    }
}
