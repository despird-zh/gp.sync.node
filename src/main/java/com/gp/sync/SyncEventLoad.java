package com.gp.sync;

import com.gp.disruptor.EventPayload;
import com.gp.disruptor.EventType;

public class SyncEventLoad extends EventPayload{

	public SyncEventLoad() {
		this.setEventType(EventType.SYNC);
	}

	public SyncMessage getData() {
		return data;
	}

	public void setPayload(SyncMessage data) {
		this.data = data;
	}

	private SyncMessage data;

}
