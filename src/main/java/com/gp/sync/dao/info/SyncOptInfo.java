package com.gp.sync.dao.info;

import com.gp.info.TraceableInfo;

public class SyncOptInfo extends TraceableInfo<Integer>{

	private static final long serialVersionUID = -7327001231571154525L;

	private String optionGroup;
	
	private String optionKey;
	
	private String optionValue;

	private String description;

	public String getOptionGroup() {
		return optionGroup;
	}

	public void setOptionGroup(String optionGroup) {
		this.optionGroup = optionGroup;
	}

	public String getOptionKey() {
		return optionKey;
	}

	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
