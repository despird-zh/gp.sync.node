package com.gp.sync.web.socket;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

public class SyncNodeSessionRegistry {

	static Logger LOGGER = LoggerFactory.getLogger(SyncNodeSessionRegistry.class);
	
	private ConcurrentMap<String, WebSocketSession> syncNodeMap = new ConcurrentHashMap<String, WebSocketSession>();
	
	/**
	 * Add the node session into the memory repository 
	 **/
	public void addNodeSession(String uname, WebSocketSession session) {
		
		LOGGER.debug("add node session: {}" , session.getPrincipal().getName());
		syncNodeMap.put(uname, session);
	}
	
	/**
	 * Get the node session out of the memory repository 
	 **/
	public WebSocketSession getNodeSession(String websocketSID) {
		
		return syncNodeMap.get(websocketSID);
	}
	
	/**
	 * Remove the session out of memory repository 
	 **/
	public WebSocketSession removeNodeSession(String uname) {
		
		LOGGER.debug("remove node session: {}" , uname);
		return syncNodeMap.remove(uname);
	}
	
	public Collection<String> allKeys() {
		return syncNodeMap.keySet();
	}
}
