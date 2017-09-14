package com.gp.svc;

import com.gp.sync.message.SyncNotifyMessage;

public interface MessageSender {

	public void sendNotifToUser(SyncNotifyMessage event,String sessionId);
	
	
	
}
