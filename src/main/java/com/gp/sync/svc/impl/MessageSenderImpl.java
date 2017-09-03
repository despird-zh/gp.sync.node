package com.gp.sync.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Service;

import com.gp.sync.message.SyncNotifyMessage;
import com.gp.sync.svc.MessageSender;

import com.gp.sync.web.socket.SyncNodeSessionRegistry;

//@Service
//public class MessageSenderImpl implements MessageSender{
//
//	@Autowired
//	SyncNodeSessionRegistry nodeSessionRegistry;
//	
//	@Autowired
//	SimpMessageSendingOperations messageTemplate;
//
//	private String qName = "/queue/response";
//
//	private MessageHeaders createHeaders(String sessionId) {
//		
//	    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//	    headerAccessor.setSessionId(sessionId);
//	    headerAccessor.setLeaveMutable(true);
//	    return headerAccessor.getMessageHeaders();
//	}
//
//	@Override
//	public void sendNotifToUser(SyncNotifMessage event,String sessionId) {
//	    messageTemplate.convertAndSendToUser(sessionId,qName,event,createHeaders(sessionId));
//	}
//}
