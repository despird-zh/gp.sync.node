package gp.sync.ws;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.gp.web.BaseController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServiceRestClient {
		
    public static void main(String... argv) throws Exception{
    	
    		RestTemplate restTemplate = new RestTemplate();
    		
    		String uri = "http://localhost:8080/gpapi/authenticate.do";
    		HttpHeaders headers = new HttpHeaders();
    	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	    Map<String,String> param = new HashMap<String,String>();
    	    param.put("principal", "dev1");
    	    param.put("credential", "1");
    	    param.put("audience", "j-client");
    	    
    	    String entityBody = BaseController.JACKSON_MAPPER.writeValueAsString(param);
    	    
    	    HttpEntity<String> entity = new HttpEntity<String>(entityBody, headers);
    	    
    		ResponseEntity<String> result = restTemplate.exchange(uri, 
    				HttpMethod.POST, 
    				entity, String.class);
    		
    		System.out.println(result.getBody());
    }
}
