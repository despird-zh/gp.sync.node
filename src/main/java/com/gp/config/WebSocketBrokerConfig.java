package com.gp.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import com.gp.sync.SyncConstants;
import com.gp.sync.web.socket.AuthenChannelInterceptorAdapter;
import com.gp.sync.web.socket.SyncHandlerDecorator;
import com.gp.sync.web.socket.SyncHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@ComponentScan(basePackages = { 
	"com.gp.sync.web.socket"
 })
@Order(Ordered.HIGHEST_PRECEDENCE + ServiceConfigurer.SERVICE_PRECEDENCE + 40)
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	static Logger LOGGER = LoggerFactory.getLogger(WebSocketBrokerConfig.class);
	
	@Autowired
	public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
	     try {
			this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
		} catch (Exception e) {
			LOGGER.debug("Fail to assign the authenticationManager in WebSocket broker config");
		}
	}

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/queue", "/exchange/");
        config.setApplicationDestinationPrefixes( SyncConstants.WS_APP_PREFIX );//gpwsi
        /**
         * The Ant Path Matcher setting must ignore, because it affect the /user/bla/bla...
         * Message forwarding.
        	 * config.setPathMatcher(new AntPathMatcher("."));
         */
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(SyncConstants.WS_ENDPOINT )//
        		.setHandshakeHandler(new SyncHandshakeHandler())
        		.setAllowedOrigins("*");
    }
    
    @Override
	public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
    		registration.addDecoratorFactory(handlerDecoratorFactory());
		registration.setMessageSizeLimit(256 * 1024); // 256K
		registration.setSendBufferSizeLimit(512 * 1024); // 512K
		super.configureWebSocketTransport(registration);
	}

	@Bean
	public WebSocketHandlerDecoratorFactory handlerDecoratorFactory() {
		return new WebSocketHandlerDecoratorFactory() {
			@Override
			public WebSocketHandler decorate(final WebSocketHandler handler) {
				return new SyncHandlerDecorator(handler);
			}
		};
	}
	
	/**
	 *  prepare the authenticationMananger so as to inject it into {@link AuthenChannelInterceptorAdapter}
	 */
	private AuthenticationManager authenticationManager;
	
    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.setInterceptors(new AuthenChannelInterceptorAdapter(authenticationManager));
    }
}
