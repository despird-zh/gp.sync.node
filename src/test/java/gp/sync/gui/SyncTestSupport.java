package gp.sync.gui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gp.sync.web.socket.SyncStompClient;
import com.gp.web.BaseController;

public class SyncTestSupport {
	
	static Log log = LogFactory.getLog(SyncTestSupport.class);
	
	SyncTestMainGui main = null;
	
	SyncStompClient stompClient = null;
	
	
	public SyncTestSupport(SyncTestMainGui main) {
		this.main = main;
	}
	
	public void login(String login, String passcode, String url){
	
		main.appendLog("Start Login ...");
    		RestTemplate restTemplate = new RestTemplate();
    		
    		String uri = "http://localhost:8080/gpapi/authenticate.do";
    		HttpHeaders headers = new HttpHeaders();
    	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	    Map<String,String> param = new HashMap<String,String>();
    	    param.put("principal", login);
    	    param.put("credential", passcode);
    	    param.put("audience", "j-client");
    	    
    	    String entityBody = null;
		try {
			entityBody = BaseController.JACKSON_MAPPER.writeValueAsString(param);
    	    
	    	    HttpEntity<String> entity = new HttpEntity<String>(entityBody, headers);
	    	    
	    		ResponseEntity<String> result = restTemplate.exchange(uri, 
	    				HttpMethod.POST, 
	    				entity, String.class);
			
	    		Map<String, Object> map = BaseController.JACKSON_MAPPER.readValue(result.getBody(), new TypeReference<Map<String, Object>>(){});
	    		String token = (String)map.get("data");
	    		main.setToken(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		main.appendLog("Login Done.");
	}
	
	public void send(String url, String message, String token) {
		main.appendLog("Start send ...");
		stompClient.send(url, token, message);
		main.appendLog("send done.");
	}
	
	public void connect(String login, String passcode, String url) {
		main.appendLog("Start connect via login/pass ...");
		Map<String, StompFrameHandler> handlerMap = new HashMap<String, StompFrameHandler>();
		DevTestFrameHandler testHandler = new DevTestFrameHandler();
		testHandler.testMain = this.main;
		handlerMap.put("/topic/greetings", testHandler);
		
		stompClient = new SyncStompClient(url, handlerMap);
		
		stompClient.connect(login, passcode);
		main.appendLog("connect done.");
	}
	
	public void connect(String token, String url) {
		main.appendLog("Start connect via token ...");
		stompClient = new SyncStompClient(url);
		stompClient.connect(token);
		main.appendLog("connect done.");
	}
	
	public void disconnect() {
		main.appendLog("Start disconnect ...");
		stompClient.disconnect();
		main.appendLog("disconnect done.");
	}
	
	public boolean isReady() {
		return stompClient != null && stompClient.isReady();
	}
}
