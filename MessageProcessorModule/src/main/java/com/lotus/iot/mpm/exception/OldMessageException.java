package com.lotus.iot.mpm.exception;


import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponse;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class OldMessageException extends AbstractException {
    public OldMessageException(GeneralResponse response) {
        super(response);
    }
}
