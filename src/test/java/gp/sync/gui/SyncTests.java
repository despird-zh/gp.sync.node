package gp.sync.gui;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gp.common.IdKey;
import com.gp.common.IdKeys;
import com.gp.info.InfoId;
import com.gp.sync.message.SyncMessages;
import com.gp.sync.message.SyncPushMessage;
import com.gp.sync.message.SyncType;
import com.gp.sync.web.model.HelloMessage;

public class SyncTests {

	static ObjectMapper OBJMAPPER = new ObjectMapper();
	static {
		//SyncMessages.withInfoIdModule(OBJMAPPER);
		OBJMAPPER.enable(SerializationFeature.INDENT_OUTPUT);
	}
	Map<String, Object> dataMap = new HashMap<String, Object>();
	
	public SyncTests(){
		HelloMessage hello = new HelloMessage();
		hello.setName("Testing /test mapping");
		dataMap.put("/test", hello);
		
		hello = new HelloMessage();
		hello.setName("Testing /hello mapping");
		dataMap.put("/hello", hello);
		
		SyncPushMessage push = new SyncPushMessage();
		push.setNode("N0010");
		InfoId<Long> wid = IdKeys.getInfoId(IdKey.WORKGROUP, 31l);
		String trcCd = IdKeys.getTraceCode("N0010", wid);
		push.setTraceCode(trcCd);
		push.setType(new SyncType("cmd-wgrp-update"));
		hello = new HelloMessage();
		hello.setName("Testing push payload bla.. ");
		push.setPayload(hello);
		dataMap.put("/sync.push", push);
	}
	
	public String getTestData(String messagePath) {
		Object demodata = dataMap.get(messagePath);
		
		try {
			return OBJMAPPER.writeValueAsString(demodata);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "blank";
		}
	}
}
