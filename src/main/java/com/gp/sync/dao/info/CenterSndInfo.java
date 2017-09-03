package com.gp.sync.dao.info;

import java.util.Date;

import com.gp.info.TraceableInfo;

public class CenterSndInfo extends TraceableInfo<Long> {

	private static final long serialVersionUID = 4442822375883228289L;

	private String entityCode;
	
	private String nodeCode;
	
	private Date sendTime;
	
	private String bloomData;
	
	private String sendData;
	
	private String state;

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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getBloomData() {
		return bloomData;
	}

	public void setBloomData(String bloomData) {
		this.bloomData = bloomData;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSendData() {
		return sendData;
	}

	public void setSendData(String sendData) {
		this.sendData = sendData;
	}
	
	
}
