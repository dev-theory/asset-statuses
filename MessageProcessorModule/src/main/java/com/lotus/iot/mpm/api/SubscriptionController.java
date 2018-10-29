package com.lotus.iot.mpm.api;

import com.lotus.iot.mpm.api.objectmodel.request.SubscribeRequest;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponseCode;
import com.lotus.iot.mpm.eventhandler.EventHandlerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@RestController
@RequestMapping("subscription")
public class SubscriptionController extends AbstractController {
    private EventHandlerFacade eventHandlerFacade;

    @Autowired
    public SubscriptionController(EventHandlerFacade eventHandlerFacade) {
        this.eventHandlerFacade = eventHandlerFacade;
    }

    @RequestMapping(value = "asset/event/subscribe", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity subscribeToAssetStatusEvent(@RequestBody SubscribeRequest subscribeRequest) {
        eventHandlerFacade.subscribe(subscribeRequest);
        return generateResponse(GeneralResponseCode.SUCCESS);
    }
}
