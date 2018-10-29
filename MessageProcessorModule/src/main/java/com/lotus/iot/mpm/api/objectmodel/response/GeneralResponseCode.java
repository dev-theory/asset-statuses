package com.lotus.iot.mpm.api.objectmodel.response;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class GeneralResponseCode {
    public static final GeneralResponse SUCCESS = new GeneralResponse(200, "successful");
    public static final GeneralResponse INTERNAL_SERVICE_ERROR = new GeneralResponse(500, "Something went wrong");
    public static final GeneralResponse INVALID_URL = new GeneralResponse(404, "URL does not exist");
    public static final GeneralResponse INVALID_INPUT = new GeneralResponse(400, "Invalid Input");
    public static final GeneralResponse INVALID_DATE_FORMAT = new GeneralResponse(400, "Invalid Date Format");
    public static final GeneralResponse INVALID_STATUS_TYPE = new GeneralResponse(400, "Provided status type does not exist");
    public static final GeneralResponse INVALID_EVENT_TYPE = new GeneralResponse(400, "Provided event type does not exist");
    public static final GeneralResponse NO_ASSET_RECORD_FOUND = new GeneralResponse(400, "No status record found for this asset");

    public static final GeneralResponse OUT_DATED = new GeneralResponse(422, "Newer status is in the system");
}

