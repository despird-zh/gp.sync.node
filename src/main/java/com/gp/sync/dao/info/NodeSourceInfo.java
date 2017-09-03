package com.gp.sync.dao.info;

import java.util.Date;

import com.gp.info.TraceableInfo;

public class NodeSourceInfo extends TraceableInfo<Long>{

	private static final long serialVersionUID = 1495921870443249818L;

	private String entityCode;
	
	private String nodeCode;
	
	private Date lastPushTime;
	
	private Long lastPushOwm;
	
	private Date lastPullTime;
	
	private Long lastPullOwm;

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public Date getLastPushTime() {
		return lastPushTime;
	}

	public void setLastPushTime(Date lastPushTime) {
		this.lastPushTime = lastPushTime;
	}

	public Long getLastPushOwm() {
		return lastPushOwm;
	}

	public void setLastPushOwm(Long lastPushOwm) {
		this.lastPushOwm = lastPushOwm;
	}

	public Date getLastPullTime() {
		return lastPullTime;
	}

	public void setLastPullTime(Date lastPullTime) {
		this.lastPullTime = lastPullTime;
	}

	public Long getLastPullOwm() {
		return lastPullOwm;
	}

	public void setLastPullOwm(Long lastPullOwm) {
		this.lastPullOwm = lastPullOwm;
	}
	
	
}
