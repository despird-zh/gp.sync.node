package com.gp.sync.web.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import gp.sync.ws.MySessionHandler;

public class SyncStompClient {
	
	static Logger LOGGER = LoggerFactory.getLogger(SyncStompClient.class);
	
	private String url;
	
	private MessageConverter messageConverter = null;
	
	private WebSocketStompClient stompClient = null;
	
	private StompSession stompSession = null;
	
	public SyncStompClient(String url) {
		this.url = url;
		this.messageConverter = new MappingJackson2MessageConverter();
	}
	
	public SyncStompClient(String url, MessageConverter messageConverter) {
		this.url = url;
		this.messageConverter = messageConverter;
	}
	
	public void connect(String login, String passcode) {
		
		WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(this.messageConverter);
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        StompSessionHandler sessionHandler = new MySessionHandler();
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("passcode", passcode);
        headers.add("login", login);
        
        ListenableFuture<StompSession> listenableFuture = stompClient.connect(url, headers, sessionHandler);
        SuccessCallback<StompSession> successCallback = new SuccessCallback<StompSession>() {  
            @Override  
            public void onSuccess(StompSession session) {  
                stompSession = session;
                LOGGER.debug("success get stomp session");
            }  
        }; 
        FailureCallback failureCallback = new FailureCallback() {  
            @Override  
            public void onFailure(Throwable throwable) {  
            		stompSession = null; 
            		LOGGER.debug("fail get stomp session");
            }  
        }; 
        listenableFuture.addCallback(successCallback, failureCallback);
	}
	
	public void connect(String token) {
		
		WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(this.messageConverter);
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        StompSessionHandler sessionHandler = new MySessionHandler();
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("token", token);
        
        ListenableFuture<StompSession> listenableFuture = stompClient.connect(url, headers, sessionHandler);
        SuccessCallback<StompSession> successCallback = new SuccessCallback<StompSession>() {  
            @Override  
            public void onSuccess(StompSession session) {  
                stompSession = session;
                LOGGER.debug("success get stomp session");
            }  
        }; 
        FailureCallback failureCallback = new FailureCallback() {  
            @Override  
            public void onFailure(Throwable throwable) {  
            		stompSession = null; 
            		LOGGER.debug("fail get stomp session");
            }  
        }; 
        listenableFuture.addCallback(successCallback, failureCallback);
	}

	public void send(String url, String token, String msg) {
		
		StompHeaders headers = new StompHeaders();
		headers.setDestination(url);
		headers.set("token", token);
		
		//stompSession.send("/app/hello", "{\"name\":\"Client\"}".getBytes());

		stompSession.send(headers, msg.getBytes());
	}
	
	public void disconnect() {
		
		this.stompSession.disconnect();
		this.stompClient.stop();
	}
	
	public boolean isReady() {
		return null != this.stompSession;
	}
}