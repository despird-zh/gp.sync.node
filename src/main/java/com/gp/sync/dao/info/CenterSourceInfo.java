package com.gp.sync.dao.info;

import java.util.Date;

import com.gp.info.TraceableInfo;

public class CenterSourceInfo extends TraceableInfo<Long>  {

	private static final long serialVersionUID = 259778966576502543L;

	private String entityCode;
	
	private String nodeCode;
	
	private Date lastReceiveTime;
	
	private Long lastReceiveOwm;
	
	private Date lastSendTime;
	
	private Long lastSendOwm;

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

	public Date getLastReceiveTime() {
		return lastReceiveTime;
	}

	public void setLastReceiveTime(Date lastReceiveTime) {
		this.lastReceiveTime = lastReceiveTime;
	}

	public Long getLastReceiveOwm() {
		return lastReceiveOwm;
	}

	public void setLastReceiveOwm(Long lastReceiveOwm) {
		this.lastReceiveOwm = lastReceiveOwm;
	}

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public Long getLastSendOwm() {
		return lastSendOwm;
	}

	public void setLastSendOwm(Long lastSendOwm) {
		this.lastSendOwm = lastSendOwm;
	}
	
	
}
