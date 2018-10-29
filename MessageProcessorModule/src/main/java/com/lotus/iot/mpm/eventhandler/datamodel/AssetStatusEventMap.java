package com.lotus.iot.mpm.eventhandler.datamodel;

import com.lotus.iot.mpm.eventhandler.objectmodel.Asset;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@Repository
public class AssetStatusEventMap {
    private Map<String, Asset> map = new HashMap<>();

    void put(String key, Asset asset) {
        map.put(key, asset);
    }

    Asset get(String key) {
        return map.get(key);
    }
}
