package com.lotus.iot.mpm.exception;

import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponse;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public abstract class AbstractException extends Exception {
    private final GeneralResponse response;

    AbstractException(GeneralResponse response) {
        this.response = response;
    }

    public GeneralResponse getResponse() {
        return response;
    }
}
