package com.gp.sync;

import com.gp.disruptor.EventDispatcher;
import com.gp.exception.BaseException;
import com.gp.launcher.CoreInitializer;
import com.gp.launcher.LifecycleHooker;

public class SyncInitializer extends CoreInitializer{

	public SyncInitializer() throws BaseException {
		super();
	}

	@Override
	public LifecycleHooker setupLifecycleHooker() throws BaseException {
		
		return new LifecycleHooker("SyncLifecycleHooker", 10){

			@Override
			public void initial() {
				
				SyncHooker syncHooker = new SyncHooker();
				EventDispatcher.getInstance().regEventHooker( syncHooker );
				sendFeedback(false, "SyncLifecycleHooker initial done");
			}

			@Override
			public void startup() { }

			@Override
			public void shutdown() {	}

		};
	}

}
