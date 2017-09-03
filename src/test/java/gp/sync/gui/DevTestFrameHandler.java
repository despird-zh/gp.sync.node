package gp.sync.gui;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import com.gp.sync.web.model.Greeting;
import com.gp.sync.web.socket.SyncClientSessionHandler;

public class DevTestFrameHandler implements StompFrameHandler{

	static Logger log = LoggerFactory.getLogger(SyncClientSessionHandler.class);
	
	SyncTestMainGui testMain = null;
	
	@Override
	public Type getPayloadType(StompHeaders headers) {
		return Greeting.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		Greeting message = (Greeting) payload;
		log.info("Received: {} ", message.getContent());
		if( null != testMain) {
			testMain.appendReceived(message.getContent());
		}
	}

}
