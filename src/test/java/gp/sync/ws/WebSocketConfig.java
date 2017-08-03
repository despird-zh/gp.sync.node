package gp.sync.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    		//HttpSessionHandshakeInterceptor intercepter = new SessionInterceptor();
    		//registry.addHandler(new MyHandler(), "/myHandler")
        //    .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    class MyHandler implements WebSocketHandler{

		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			// TODO Auto-generated method stub
		}

		@Override
		public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean supportsPartialMessages() {
			// TODO Auto-generated method stub
			return false;
		}
    	
    }
}
