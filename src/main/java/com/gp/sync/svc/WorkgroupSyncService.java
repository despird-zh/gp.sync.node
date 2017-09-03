package com.gp.sync.svc;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkgroupSyncService {

	List<Map<String, Object>> filterSyncData(Date from, Date to, Long owm);
	
}
