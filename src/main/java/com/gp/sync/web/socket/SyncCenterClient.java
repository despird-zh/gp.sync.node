package com.gp.sync.web.socket;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
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

import com.gp.sync.SyncConstants;
import com.gp.sync.message.SyncMessages;
import com.gp.sync.message.SyncPushMessage;

/**
 * Client to connect sync WebSocket server
 * 
 * @author gdiao
 * @version 0.1 2017-7-10
 **/
public class SyncCenterClient {
	
	static Logger LOGGER = LoggerFactory.getLogger(SyncCenterClient.class);
	
	private String url;
	// stomp client
	private WebSocketStompClient stompClient = null;
	// stomp session
	private StompSession stompSession = null;
	// extra frame handler
	private Map<String, StompFrameHandler> handlerMap = null;
	// stomp session handler
	private StompSessionHandler sessionHandler = null;

	/**
	 * The default sync center client constructor 
	 **/
	private SyncCenterClient() {
		WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter( new MappingJackson2MessageConverter() );
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
	}
	
	/**
	 * constructor with URL parameter 
	 **/
	public SyncCenterClient(String url) {
		this();
		this.url = url;
        sessionHandler = new SyncClientSessionHandler(this.handlerMap);
	}
	
	/**
	 * constructor with URL and handlerMap parameters
	 * @param url the connection url
	 * @param handlerMap the map of url path and responsive frame handler.
	 **/
	public SyncCenterClient(String url, Map<String, StompFrameHandler> handlerMap) {
		this();
		this.url = url;
        sessionHandler = new SyncClientSessionHandler(this.handlerMap);
	}
	
	/**
	 * Connect to server with login and passcode,
	 * header definition refer to {@link SyncConstants}
	 * 
	 * @param login the login, header key: login
	 * @param passcode the password , header key: passcode
	 **/
	public void connect(String login, String passcode) {
		
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add(SyncConstants.WS_HEADER_PASSWORD, passcode);
        headers.add(SyncConstants.WS_HEADER_USERNAME, login);
        
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
	
	/**
	 * Connect to server with token,
	 * header definition refer to {@link SyncConstants}
	 * 
	 * @param token the Jwt token
	 **/
	public void connect(String token) {
				
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add(SyncConstants.WS_HEADER_TOKEN, token);
        
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

	/**
	 * Send String message
	 * 
	 * @param messageMappingUrl the message destination, eg. the message mapping path
	 * @param msg the message string  
	 **/
	public void send(String messageMappingUrl, String msg) {
		
		StompHeaders headers = new StompHeaders();
		headers.setDestination(messageMappingUrl);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Sending message to {} : {}", messageMappingUrl, msg);
		}
		stompSession.send(headers, msg.getBytes());
	}
	
	/**
	 * Send Sync Push message
	 * 
	 * @param messageMappingUrl the message destination, eg. the message mapping path
	 * @param pushMsg the message {@link SyncPushMessage}  
	 **/
	public void send(String messageMappingUrl, SyncPushMessage pushMsg) {
		
		StompHeaders headers = new StompHeaders();
		headers.setDestination(messageMappingUrl);
		
		byte[] msg = SyncMessages.wrapPushMessage(pushMsg);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Sending message to {} : {}", messageMappingUrl, new String(msg));
		}
		stompSession.send(headers, msg);
	}

	/**
	 * Disconnect the client to server 
	 **/
	public void disconnect() {
		
		this.stompSession.disconnect();
		this.stompClient.stop();
		this.stompSession = null;
	}
	
	/**
	 * Check if the client is ready for message sending
	 **/
	public boolean isReady() {
		return null != this.stompSession;
	}
}
