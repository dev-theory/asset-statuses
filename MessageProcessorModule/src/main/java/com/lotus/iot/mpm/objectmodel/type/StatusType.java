package com.lotus.iot.mpm.objectmodel.type;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public enum StatusType {
    NORMAL("has recovered"),
    WARNING("is about to fail"),
    ERROR("has failed");

    private String description;

    StatusType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
