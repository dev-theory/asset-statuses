package com.lotus.iot.mpm.api.objectmodel.response;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class GeneralResponse {
    @JsonProperty("response_status")
    private Integer status;
    @JsonProperty("response_message")
    private String message;

    public GeneralResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
