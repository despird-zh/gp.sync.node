package com.gp.sync.web.socket;

import org.springframework.messaging.simp.stomp.StompHeaders;

/**
 * Sync session handler hooker to monitor the message received,
 * Most time it's just used for debug purpose.
 * 
 * @author gdiao 
 * @version 0.1 2017-8-10 
 **/
public interface SyncHandlerHooker {

	/**
	 * hook the handler process
	 * 
	 **/
	public void onHandleFrame(StompHeaders headers, Object payload) ;
}
