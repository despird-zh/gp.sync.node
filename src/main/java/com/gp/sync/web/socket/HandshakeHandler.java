package com.gp.sync.web.socket;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.gp.sync.GPrincipal;

public class HandshakeHandler extends DefaultHandshakeHandler {

	Logger log = LoggerFactory.getLogger(HandshakeHandler.class);
	
	
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                    Map<String, Object> attributes) {
    		
    		HttpHeaders headers = request.getHeaders();

    		String mapHeaders = headers.toSingleValueMap().toString();
    		log.info("determine attr : {}", mapHeaders);
    		
    		String login = headers.toSingleValueMap().get("login");
    		String passcode = headers.toSingleValueMap().get("passcode");
    		
        return new GPrincipal(login, passcode);
    }

}
