package gp.sync.gui;

import java.util.HashMap;
import java.util.Map;

public class SyncTests {

	Map<String, Object> dataMap = new HashMap<String, Object>();
	
	public SyncTests(){
		dataMap.put("/test", "{ \"tkey\": \"hello blabal...\" }");
		
	}
	
	public String getTestData(String messagePath) {
		return (String)dataMap.get(messagePath);
	}
}
