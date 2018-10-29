package com.lotus.iot.mpm.validation;

import com.lotus.iot.mpm.api.AbstractController;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponseCode;
import com.lotus.iot.mpm.exception.ValidationException;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class ValidationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    public static StatusType statusType(String input) throws ValidationException {
        try {
            return StatusType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.error("StatusType {} does not exist in the system.", input);
            throw new ValidationException(GeneralResponseCode.INVALID_STATUS_TYPE);
        }
    }
}
