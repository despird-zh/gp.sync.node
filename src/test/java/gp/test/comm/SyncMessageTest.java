package gp.test.comm;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gp.common.IdKey;
import com.gp.common.IdKeys;
import com.gp.info.InfoId;
import com.gp.sync.message.SyncMessages;
import com.gp.sync.message.SyncNotifyMessage;
import com.gp.sync.message.SyncPushMessage;
import com.gp.sync.message.SyncType;
import com.gp.sync.web.model.HelloMessage;

import gp.sync.gui.SyncTests;

public class SyncMessageTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SyncTests tests = new SyncTests();
		String pushMsg = tests.getTestData("/sync.push");
		
		SyncPushMessage msg = SyncMessages.parsePushMessage(pushMsg.getBytes());
		
		System.out.println("------ Push Payload: "+msg.getPayload());
		
		SyncNotifyMessage notifyMsg = new SyncNotifyMessage();
		
		notifyMsg.setCenter("xxcenter001");
		InfoId<Long> wid = IdKeys.getInfoId(IdKey.WORKGROUP, 31l);
		String trcCd = IdKeys.getTraceCode("xxcenter001", wid);
		notifyMsg.setTraceCode(trcCd);
		notifyMsg.setType(new SyncType("cmd-wgrp-feedback"));
		HelloMessage hello = new HelloMessage();
		hello.setName("blabla demo test payload");
		notifyMsg.setPayload(hello);
		
		String data = SyncMessages.MESSAGE_MAPPER.writeValueAsString(notifyMsg);
		
		System.out.println("------ Notif Msg: "+ data);
		
		SyncNotifyMessage notif = SyncMessages.MESSAGE_MAPPER.readValue(data.getBytes(), SyncNotifyMessage.class);
		System.out.println("notif - center : "+ notif.getCenter());
		System.out.println("notif - type : "+ notif.getType());
		System.out.println("notif - payload : "+ notif.getPayload());
		System.out.println("notif - trace :"+ notif.getTraceCode());
	}

}
