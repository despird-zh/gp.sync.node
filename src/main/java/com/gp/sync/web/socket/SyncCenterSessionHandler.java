package com.gp.sync.web.socket;


import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.gp.sync.message.SyncNotifyMessage;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 
 * 
 **/
public class SyncCenterSessionHandler extends StompSessionHandlerAdapter {
	
	static Logger log = LoggerFactory.getLogger(SyncCenterSessionHandler.class);
	
	private Map<String, StompFrameHandler> handlerMap = null;
	private SyncHandlerHooker handlerHooker = null;
	
	public SyncCenterSessionHandler(Map<String, StompFrameHandler> handlerMap, SyncHandlerHooker handlerHooker) {
		this.handlerMap = handlerMap;
		this.handlerHooker = handlerHooker;
	}
	
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    	
    		log.info("New session: {}", session.getSessionId());
    		
        session.subscribe("/topic/sync.notify", this);
        log.debug("subscribed to: {}", "/topic/sync.notify");
        
        session.subscribe("/user/queue/sync.notify", this);
        log.debug("subscribed to: {}", "/user/queue/sync.notify");
        
        if(MapUtils.isNotEmpty(this.handlerMap)) {
	        for(Map.Entry<String, StompFrameHandler> entry: handlerMap.entrySet()) {
	        	
	        		session.subscribe(entry.getKey(), entry.getValue());
	        		log.debug("subscribed to: {}", entry.getKey());
	        }
        }
        
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return SyncNotifyMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
    	
    		SyncNotifyMessage message =	(SyncNotifyMessage) payload;
    		if(null != message) {
	        log.info("Received: {} - {}", message.getType(), message.getTraceCode());
	        log.debug("Received: {}", message.getPayload());
	        if(handlerHooker != null) {
	        		this.handlerHooker.onHandleFrame(headers, payload);
	        }
    		}else {
    			log.debug("Message payload is null.");
    			log.debug("Message header : {}", headers.toSingleValueMap());
    		}

    }
}
