package com.gp.sync.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;

import com.gp.sync.web.model.SyncMessage;
import com.gp.sync.web.socket.AgentSessionRegistry;

public class MessageSenderImpl implements MessageSender{

	@Autowired
	AgentSessionRegistry webAgentSessionRegistry;
	
	@Autowired
	SimpMessageSendingOperations messageTemplate;

	private String qName = "/queue/response";

	private MessageHeaders createHeaders(String sessionId) {
		
	    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
	    headerAccessor.setSessionId(sessionId);
	    headerAccessor.setLeaveMutable(true);
	    return headerAccessor.getMessageHeaders();
	}

	@Override
	public void sendEventToClient(SyncMessage event, String sessionId) {
	    messageTemplate.convertAndSendToUser(sessionId,qName,event,createHeaders(sessionId));
	}
}
