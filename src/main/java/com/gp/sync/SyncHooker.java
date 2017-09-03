package com.gp.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.disruptor.EventHooker;
import com.gp.disruptor.EventPayload;
import com.gp.disruptor.EventType;
import com.gp.exception.RingEventException;

public class SyncHooker extends EventHooker<SyncEventLoad>{

	public static Logger LOGGER = LoggerFactory.getLogger(SyncHooker.class);
	
	public SyncHooker() {
		this(EventType.SYNC);
	}
	
	public SyncHooker(EventType eventType) {
		super(eventType);
	}

	@Override
	public void processPayload(EventPayload payload) throws RingEventException {
		
		SyncEventLoad data = (SyncEventLoad) payload;
		
		LOGGER.debug("event sync: {}", data.getData());
	}

}
