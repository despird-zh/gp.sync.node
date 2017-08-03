package gp.sync.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class WebSocketDisconnectHandler <S>
implements ApplicationListener<SessionDisconnectEvent> {
	static Logger LOGGER = LoggerFactory.getLogger(WebSocketDisconnectHandler.class);
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		// TODO Auto-generated method stub
		LOGGER.info("offline: {}","SessionDisconnectEvent");
	}

}
