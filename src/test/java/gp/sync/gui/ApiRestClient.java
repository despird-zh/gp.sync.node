package gp.sync.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class ApiRestClient {
	
	static Logger LOGGER = LoggerFactory.getLogger(ApiRestClient.class);
    
    public void callHttpApi(String url, String token, String postData) throws Exception{
    	
		RestTemplate restTemplate = new RestTemplate();
		
		String uri = url;
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    headers.add("Authorization", token);

	    HttpEntity<String> entity = new HttpEntity<String>(postData, headers);
	    
		ResponseEntity<String> result = restTemplate.exchange(uri, 
				HttpMethod.POST, 
				entity, String.class);
		
		LOGGER.debug("Response: {} ",result.getBody());
}
}
