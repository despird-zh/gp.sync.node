package com.gp.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.core.AppContextHelper;
import com.gp.sync.message.SyncPullMessage;
import com.gp.sync.message.SyncPushMessage;

/**
 * Wrap all the business logic, 
 * 
 * @author gdiao
 * @version 0.1 2017-5-6
 **/
public class SyncNodeEngine {
	
	static Logger LOGGER = LoggerFactory.getLogger(SyncNodeEngine.class);
	
	private static SyncNodeEngine instance;
		
	public static SyncNodeEngine getInstance() {
		
		if( instance == null) {
			instance = new SyncNodeEngine();
			AppContextHelper.autowireBean(instance);
		}
		
		return instance;
	}
	
	public void handlePushMessage(SyncPushMessage pushMessage) {
		
	}
	
	public void handlePullMessage(SyncPullMessage pullMessage) {
		
	}
}
