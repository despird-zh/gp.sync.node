package com.gp.sync.dao.info;

import java.util.Date;

import com.gp.info.TraceableInfo;

public class NodePullInfo extends TraceableInfo<Long>{

	private static final long serialVersionUID = -4887601189590299479L;

	private String entityCode;
	
	private String nodeCode;
	
	private String bloomData;
	
	private Date pullTime;
	
	private String pullData;

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

	public String getBloomData() {
		return bloomData;
	}

	public void setBloomData(String bloomData) {
		this.bloomData = bloomData;
	}

	public Date getPullTime() {
		return pullTime;
	}

	public void setPullTime(Date pullTime) {
		this.pullTime = pullTime;
	}

	public String getPullData() {
		return pullData;
	}

	public void setPullData(String pullData) {
		this.pullData = pullData;
	}
	
	
}
