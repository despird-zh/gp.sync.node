package com.gp.sync;

import com.gp.common.IdKeys;
import com.gp.disruptor.EventDispatcher;
import com.gp.exception.BaseException;
import com.gp.launcher.CoreInitializer;
import com.gp.launcher.LifecycleListener;

public class SyncCoreInitializer extends CoreInitializer{

	public SyncCoreInitializer() throws BaseException {
		super();
	}

	@Override
	public LifecycleListener setupLifecycleHooker() throws BaseException {
		
		return new LifecycleListener("SyncLifecycleHooker", 10){

			@Override
			public void initial() {
				// add sync hooker to disruptor engine
				SyncEventListener syncHooker = new SyncEventListener();
				EventDispatcher.getInstance().regEventHooker( syncHooker );
				// Register the IdKey enums to IdKeys helper class.
				IdKeys.addIdentifier(SyncIdKey.values());
				
				sendFeedback(false, "SyncLifecycleHooker initial done");
				
			}

			@Override
			public void startup() { }

			@Override
			public void shutdown() {	}

		};
	}

}
