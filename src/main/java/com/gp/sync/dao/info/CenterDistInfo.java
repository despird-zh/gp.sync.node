package com.gp.sync.dao.info;

import java.util.Date;

import com.gp.info.TraceableInfo;

public class CenterDistInfo  extends TraceableInfo<Long> {

	private static final long serialVersionUID = 946613368288782447L;

	private Long msgId;
	
	private Long sendId;
	
	private String targetEntityCode;
	
	private String targetNodeCode;
	
	private Date distributeTime;
	
	private Boolean syncDone;

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getSendId() {
		return sendId;
	}

	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}

	public String getTargetEntityCode() {
		return targetEntityCode;
	}

	public void setTargetEntityCode(String targetEntityCode) {
		this.targetEntityCode = targetEntityCode;
	}

	public String getTargetNodeCode() {
		return targetNodeCode;
	}

	public void setTargetNodeCode(String targetNodeCode) {
		this.targetNodeCode = targetNodeCode;
	}

	public Date getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}

	public Boolean getSyncDone() {
		return syncDone;
	}

	public void setSyncDone(Boolean syncDone) {
		this.syncDone = syncDone;
	}
	
}
