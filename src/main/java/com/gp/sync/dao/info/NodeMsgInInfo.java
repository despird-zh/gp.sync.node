package com.gp.sync.dao.info;

import com.gp.info.TraceableInfo;

public class NodeMsgInInfo extends TraceableInfo<Long>{

	private static final long serialVersionUID = -5237483219294576035L;

	private Long pullId;
	
	private String entityCode;
	
	private String nodeCode;
	
	private String traceCode;
	
	private Long owm;
	
	private String syncCommand;
	
	private String msgData;

	public Long getPullId() {
		return pullId;
	}

	public void setPullId(Long pullId) {
		this.pullId = pullId;
	}

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

	public String getTraceCode() {
		return traceCode;
	}

	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}

	public Long getOwm() {
		return owm;
	}

	public void setOwm(Long owm) {
		this.owm = owm;
	}

	public String getSyncCommand() {
		return syncCommand;
	}

	public void setSyncCommand(String syncCommand) {
		this.syncCommand = syncCommand;
	}

	public String getMsgData() {
		return msgData;
	}

	public void setMsgData(String msgData) {
		this.msgData = msgData;
	}

}
