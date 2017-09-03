package com.gp.sync.web.socket;

import java.security.Principal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.gp.sync.SyncConstants;
import com.gp.sync.web.JwtAuthenToken;
import com.gp.sync.web.UserPasswordAuthenToken;

public class AuthenChannelInterceptorAdapter extends ChannelInterceptorAdapter {
	    
    static Logger LOGGER = LoggerFactory.getLogger(AuthenChannelInterceptorAdapter.class);

    private AuthenticationManager authenManager = null;
    
    public AuthenChannelInterceptorAdapter(AuthenticationManager authenManager) {
    		this.authenManager = authenManager;
    }
    
    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        
    		final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        Principal principal = accessor.getUser();
        
        if (StompCommand.CONNECT == accessor.getCommand()) {
        		
        		if(principal == null)
        			principal = parseFromHeader(accessor);
        		
        		if(principal != null) {
	        		LOGGER.debug("Try to authorization during connect : {}", principal.getName());
	           
	        		Authentication authen = (Authentication) principal;
	        		Authentication passedAuthen = authenManager.authenticate(authen);
		        
	        		accessor.setUser(passedAuthen);
        		}else {
        			throw new BadCredentialsException("Invalid Backend User Credentials");
        		}
	    }
        return message;
    }
    
    /**
     * Extract the Authentication from the message header 
     **/
    private Authentication parseFromHeader(StompHeaderAccessor accessor) {

		String login = accessor.getFirstNativeHeader(SyncConstants.WS_HEADER_USERNAME);
        String passcode = accessor.getFirstNativeHeader(SyncConstants.WS_HEADER_PASSWORD);
        String audience = accessor.getFirstNativeHeader(SyncConstants.WS_HEADER_AUDIENCE);
        String token = accessor.getFirstNativeHeader(SyncConstants.WS_HEADER_TOKEN);
	
	    if(StringUtils.isNotEmpty(token)) {
			return new JwtAuthenToken( token);
	    }
	    else if(StringUtils.isNotEmpty(passcode) && StringUtils.isNotEmpty(login)) {
	    		UserPasswordAuthenToken rtv = new UserPasswordAuthenToken( login, passcode );
	    		rtv.setAudience(audience);
	    		return rtv;
	    }
    
	    return null;
	}
}
