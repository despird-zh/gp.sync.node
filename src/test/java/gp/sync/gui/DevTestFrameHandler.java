package gp.sync.gui;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import com.gp.sync.message.Greeting;
import com.gp.sync.web.socket.SyncCenterSessionHandler;
import com.gp.sync.web.socket.SyncHandlerHooker;

public class DevTestFrameHandler implements StompFrameHandler{

	static Logger log = LoggerFactory.getLogger(SyncCenterSessionHandler.class);
	
	private SyncHandlerHooker handlerHooker = null;
	
	public DevTestFrameHandler(SyncHandlerHooker handlerHooker) {
		this.handlerHooker = handlerHooker;
	}
	
	@Override
	public Type getPayloadType(StompHeaders headers) {
		return Greeting.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		Greeting message = (Greeting) payload;
		log.info("Received: {} ", message.getContent());
		if(handlerHooker != null) {
     		this.handlerHooker.onHandleFrame(headers, payload);
		}
	}

}
