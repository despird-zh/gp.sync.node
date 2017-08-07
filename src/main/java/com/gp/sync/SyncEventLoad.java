package com.gp.sync;

import java.util.Collection;

import com.gp.disruptor.EventPayload;
import com.gp.disruptor.EventType;

public class SyncEventLoad implements EventPayload{

	@Override
	public EventType getEventType() {
		
		return EventType.SYNC;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	private String data;

	@Override
	public Collection<EventPayload> getChainEventPayloads() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
