package com.lotus.iot.mpm.datamodel.service.objectmodel;

import com.lotus.iot.mpm.objectmodel.type.StatusType;

import java.util.Date;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
public class Status {
    private StatusType statusType;

    private Long createdAt;

    public Status(StatusType statusType, Date createdAt) {
        this.statusType = statusType;
        this.createdAt = createdAt.getTime();
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusType=" + statusType +
                ", createdAt=" + createdAt +
                '}';
    }
}
