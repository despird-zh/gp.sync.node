package com.gp.sync.dao.info;

import java.util.Date;

import com.gp.info.TraceableInfo;

public class CenterRcvInfo extends TraceableInfo<Long>{

	private static final long serialVersionUID = 6593986674756516529L;

	private String entityCode;
	
	private String nodeCode;
	
	private Date receiveTime;
	
	private String state;
	
	private String receiveData;
	
	private Long startOwm;
	
	private Long endOwm;

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

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReceiveData() {
		return receiveData;
	}

	public void setReceiveData(String receiveData) {
		this.receiveData = receiveData;
	}

	public Long getStartOwm() {
		return startOwm;
	}

	public void setStartOwm(Long startOwm) {
		this.startOwm = startOwm;
	}

	public Long getEndOwm() {
		return endOwm;
	}

	public void setEndOwm(Long endOwm) {
		this.endOwm = endOwm;
	}
	
	
}
