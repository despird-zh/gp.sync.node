package com.gp.sync.dao.info;

import java.util.Date;

import com.gp.info.TraceableInfo;

public class NodePushInfo extends TraceableInfo<Long>{

	private static final long serialVersionUID = 7069061291145878307L;

	private String entityCode;
	
	private String nodeCode;
	
	private Date pushTime;
	
	private Long startOwm;
	
	private Long endOwm;
	
	private String pushData;

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

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
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

	public String getPushData() {
		return pushData;
	}

	public void setPushData(String pushData) {
		this.pushData = pushData;
	}
	

}
