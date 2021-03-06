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
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gp.sync.message.Greeting;
import com.gp.sync.message.SyncNotifyMessage;
import com.gp.sync.web.socket.SyncCenterClient;
import com.gp.sync.web.socket.SyncCenterSessionHandler;
import com.gp.sync.web.socket.SyncHandlerHooker;
import com.gp.web.BaseController;

public class SyncTestSupport {
	
	static Log log = LogFactory.getLog(SyncTestSupport.class);
	
	SyncTestMainGui main = null;
	
	SyncCenterClient stompClient = null;
	
	ApiRestClient restClient = new ApiRestClient();
	
	SyncHandlerHooker handlerHooker = new SyncHandlerHooker() {

		@Override
		public void onHandleFrame(StompHeaders headers, Object payload) {
			if(payload instanceof SyncNotifyMessage)	{
				SyncNotifyMessage data = (SyncNotifyMessage) payload;
				main.appendReceived( (String) data.getPayload() );
			}
			else if(payload instanceof Greeting) {
				Greeting data = (Greeting) payload;
				main.appendReceived( (String) data.getContent() );
			}else {
				main.appendReceived(payload.toString());
			}
		}
		
	};
	
	public SyncTestSupport(SyncTestMainGui main) {
		this.main = main;
	}
	
	public void loginCenter(String login, String passcode, String url){
	
		main.appendLog("Start Center Login ...");
    		RestTemplate restTemplate = new RestTemplate();
    		
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
	    	    
	    		ResponseEntity<String> result = restTemplate.exchange(url, 
	    				HttpMethod.POST, 
	    				entity, String.class);
			
	    		Map<String, Object> map = BaseController.JACKSON_MAPPER.readValue(result.getBody(), new TypeReference<Map<String, Object>>(){});
	    		String token = (String)map.get("data");
	    		main.setCenterToken(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		main.appendLog("Login Center Done.");
	}
	
	public void loginNode(String login, String passcode, String url){
		
		main.appendLog("Start Node Login ...");
    		RestTemplate restTemplate = new RestTemplate();
    		
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
	    	    
	    		ResponseEntity<String> result = restTemplate.exchange(url, 
	    				HttpMethod.POST, 
	    				entity, String.class);
			
	    		Map<String, Object> map = BaseController.JACKSON_MAPPER.readValue(result.getBody(), new TypeReference<Map<String, Object>>(){});
	    		String token = (String)map.get("data");
	    		main.setNodeToken(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		main.appendLog("Login Node Done.");
	}
	
	public void send(String url, String message, String token) {
		main.appendLog("Start send ...");
		stompClient.send(url,  message);
		main.appendLog("send done.");
	}
	
	public void connect(String login, String passcode, String url) {
		main.appendLog("Start connect via login/pass ...");
		Map<String, StompFrameHandler> handlerMap = new HashMap<String, StompFrameHandler>();
		DevTestFrameHandler testHandler = new DevTestFrameHandler(this.handlerHooker);
		
		handlerMap.put("/topic/greetings", testHandler);
		SyncCenterSessionHandler sessHandler = new SyncCenterSessionHandler(handlerMap, this.handlerHooker);
		
		stompClient = new SyncCenterClient(url, sessHandler);
		
		stompClient.connect(login, passcode);
		main.appendLog("connect done.");
	}
	
	public void connect(String token, String url) {
		main.appendLog("Start connect via token ...");
		stompClient = new SyncCenterClient(url);
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
	
	public void sendNodeData(String url, String token, String postData) {
		
		try {
			restClient.callHttpApi(url, token, postData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
