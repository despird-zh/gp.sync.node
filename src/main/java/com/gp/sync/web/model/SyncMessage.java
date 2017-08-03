package com.gp.sync.web.model;

import java.util.Map;

public class SyncMessage {
	
	private String categroy;
	
	private String from;
	
	private String to;
	
	private String bloomFilter;
	
	private String payload;
	
	private Map<String,Object> params;

	public String getCategroy() {
		return categroy;
	}

	public void setCategroy(String categroy) {
		this.categroy = categroy;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getBloomFilter() {
		return bloomFilter;
	}

	public void setBloomFilter(String bloomFilter) {
		this.bloomFilter = bloomFilter;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
}
