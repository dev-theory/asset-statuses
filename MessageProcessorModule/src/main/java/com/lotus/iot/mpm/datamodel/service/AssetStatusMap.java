package com.lotus.iot.mpm.datamodel.service;


import com.lotus.iot.mpm.datamodel.service.objectmodel.Status;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Repository
@Scope("singleton")
public class AssetStatusMap {
    private Map<String, Status> map = new HashMap<>();

    Status get(String key) {
        return map.get(key);
    }

    void put(String key, StatusType statusType, Date createdAt) {
        Status status = new Status(statusType, createdAt);
        map.put(key, status);
    }

    ArrayList<String> filterByState(StatusType statusType) {
        return map.entrySet().stream().parallel().filter(item -> item.getValue().getStatusType() == statusType)
                .map(Map.Entry::getKey).collect(Collectors.toCollection(ArrayList::new));
    }
}
