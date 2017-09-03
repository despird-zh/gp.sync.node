package com.gp.sync.web.socket;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gp.common.IdKey;
import com.gp.common.IdKeys;
import com.gp.info.InfoId;
import com.gp.sync.SyncConstants;
import com.gp.sync.message.SyncMessages;
import com.gp.sync.message.SyncNotifyMessage;
import com.gp.sync.message.SyncPullMessage;
import com.gp.sync.message.SyncPushMessage;
import com.gp.sync.message.SyncType;
import com.gp.sync.web.model.ChatMessage;
import com.gp.sync.web.model.Greeting;
import com.gp.sync.web.model.HelloMessage;

/**
 * Node side subscribe the /user/queue/sync.notify to receive the {@link SyncNotifyMessage} per node
 * Node side subscribe the /topic/sync.notify to receive the {@link SyncNotifyMessage} that sent globally
 * 
 * @author gdiao
 * 
 * @version 0.1 2016-8-3
 **/
@Controller
@RequestMapping(SyncConstants.SYNC_VIEW)
public class SyncMessageController {

	Logger LOGGER = LoggerFactory.getLogger(SyncMessageController.class);
	
	@Autowired
	private SimpMessagingTemplate messaging;
	
	@Autowired
	private SyncNodeSessionRegistry nodeSessionRegistry;
	
	/**
	 * node send the SyncPushMessage, center server route the message to 
	 * other related nodes.
	 * message path: /gpapp/sync.push
	 * send feedback to /queue/sync.notify -> client: /user/queue/sync.notify
	 **/
	@MessageMapping("/sync.push")
	@SendToUser("/queue/sync.notify") 
    public SyncNotifyMessage handlePush(Message<?> message, Principal principal) {
		
		byte[] payload = (byte[]) message.getPayload();
		
		SyncPushMessage pushMsg = SyncMessages.parsePushMessage(payload);
		SyncNotifyMessage notifyMsg = new SyncNotifyMessage();
		
		notifyMsg.setCenter("xxcenter001");
		InfoId<Long> wid = IdKeys.getInfoId(IdKey.WORKGROUP, 31l);
		String trcCd = IdKeys.getTraceCode("xxcenter001", wid);
		notifyMsg.setTraceCode(trcCd);
		notifyMsg.setType(new SyncType("cmd-wgrp-feedback"));
		HelloMessage hello = new HelloMessage();
		hello.setName("blabla demo test payload");
		notifyMsg.setPayload(hello);
		
		LOGGER.debug("Receive: {}", new String(payload, StandardCharsets.UTF_8));
		
		return notifyMsg;
	}
	
	/**
	 * node subscribe to receive notification 
	 **/
	@RequestMapping("/pull")
	@ResponseBody
	public SyncPullMessage handleSpittle(HelloMessage message) { 
		
		return null;
	}
}
