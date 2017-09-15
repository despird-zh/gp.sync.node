package com.gp.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.disruptor.EventListener;
import com.gp.disruptor.EventPayload;
import com.gp.disruptor.EventType;
import com.gp.exception.RingEventException;

public class SyncEventListener extends EventListener<SyncEventLoad>{

	public static Logger LOGGER = LoggerFactory.getLogger(SyncEventListener.class);
	
	public SyncEventListener() {
		this(EventType.SYNC);
	}
	
	public SyncEventListener(EventType eventType) {
		super(eventType);
	}

	@Override
	public void processPayload(EventPayload payload) throws RingEventException {
		
		SyncEventLoad data = (SyncEventLoad) payload;
		
		LOGGER.debug("event sync: {}", data.getData());
	}

}
