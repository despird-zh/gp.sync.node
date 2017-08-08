package com.gp.sync;

import com.gp.disruptor.EventPayload;
import com.gp.disruptor.EventType;

public class SyncEventLoad extends EventPayload{

	public SyncEventLoad() {
		this.setEventType(EventType.SYNC);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	private String data;

	
}
