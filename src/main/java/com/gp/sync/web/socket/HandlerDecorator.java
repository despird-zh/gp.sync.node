package com.gp.sync.web.socket;

import java.security.Principal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import com.gp.core.AppContextHelper;

public class HandlerDecorator extends WebSocketHandlerDecorator{

	static Logger LOGGER = LoggerFactory.getLogger(HandlerDecorator.class);
	
	private AgentSessionRegistry sessionRegistry ;
	
	public HandlerDecorator(WebSocketHandler delegate) {
		super(delegate);
		
	}

	private void initialRegistry() {
		if( null == this.sessionRegistry )
			this.sessionRegistry = AppContextHelper.getSpringBean(AgentSessionRegistry.class);
	}
	
	@Override
	public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
		
		initialRegistry();
		LOGGER.info("online principal: {}", session.getPrincipal());
		super.afterConnectionEstablished(session);
		Principal p = session.getPrincipal();
		if(StringUtils.isNotBlank(p.getName()))
			sessionRegistry.addSession(p.getName(), session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
			throws Exception {
		
		initialRegistry();
		LOGGER.info("offline: {}", session);
		super.afterConnectionClosed(session, closeStatus);
		Principal p = session.getPrincipal();
		if(StringUtils.isNotBlank(p.getName()))
			sessionRegistry.removeSession(p.getName());
	}

}
