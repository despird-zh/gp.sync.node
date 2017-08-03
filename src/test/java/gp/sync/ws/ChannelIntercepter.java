package gp.sync.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

import com.gp.sync.GPrincipal;

public class ChannelIntercepter extends ChannelInterceptorAdapter{

	static Logger LOGGER = LoggerFactory.getLogger(ChannelIntercepter.class);
	
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
        		LOGGER.debug("intercep login:"+accessor.getLogin());
        		LOGGER.debug("intercep password:"+accessor.getPasscode());
            GPrincipal tp = new GPrincipal(accessor.getLogin(), accessor.getPasscode());
            accessor.setUser(tp);
        }

        return message;
    }
}
