package gp.sync.ws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.gp.sync.web.socket.AgentSessionRegistry;

@Service
public class StompConnListener implements ApplicationListener<SessionConnectEvent> {
	
	Logger log = LoggerFactory.getLogger(StompConnListener.class);
	
	@Autowired
	AgentSessionRegistry webAgentSessionRegistry;

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		
	    StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
	    MessageHeaders hds = event.getMessage().getHeaders();
	    log.debug("msg headers : {}", hds);
	    List<String> agents = sha.getNativeHeader("agent");
	    log.debug("headers : {}", agents);
	    if(agents != null && agents.size()>0) {
	    		String agentId = sha.getNativeHeader("agent").get(0);
	    
	    		String sessionId = sha.getSessionId();
	
	    		//webAgentSessionRegistry.addSession(agentId,sessionId);
	    }
	    //debug: show connected to stdout
	    webAgentSessionRegistry.show();
	
	}
}
