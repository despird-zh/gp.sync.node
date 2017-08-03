package com.gp.sync.svc;

import com.gp.sync.web.model.SyncMessage;

public interface MessageSender {

	public void sendEventToClient(SyncMessage event,String sessionId);
	
}
