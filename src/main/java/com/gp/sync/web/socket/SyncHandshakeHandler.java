package com.gp.sync.web.socket;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.gp.sync.SyncConstants;
import com.gp.sync.web.JwtAuthenToken;
import com.gp.sync.web.UserPasswordAuthenToken;

public class SyncHandshakeHandler extends DefaultHandshakeHandler {

	Logger LOGGER = LoggerFactory.getLogger(SyncHandshakeHandler.class);
	
	/**
	 * Here we not authenticate the request principal, just prepare the Principal object for
	 * further authentication and authorization in {@link AuthenChannelInterceptorAdapter } 
	 **/
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                    Map<String, Object> attributes) throws HandshakeFailureException {
    		
    		Principal gprincipal = parseFromURI(request.getURI());
    		if(null == gprincipal) {
    		
    			gprincipal = parseFromHeader(request.getHeaders());
    		}
    		
    		if(null == gprincipal || "bad1".equals(gprincipal.getName())) {
    			
			throw new HandshakeFailureException("illegal user principal");
		}
        return gprincipal;
    }

    /**
     * parse form the URI request parameters 
     **/
    private Principal parseFromURI(URI websocketUri) {
    	
    		MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(websocketUri).build().getQueryParams();
		
		List<String> tokenParam = parameters.get(SyncConstants.WS_HEADER_TOKEN);
	    List<String> passParam = parameters.get(SyncConstants.WS_HEADER_PASSWORD);
	    List<String> loginParam = parameters.get(SyncConstants.WS_HEADER_USERNAME);
	    //List<String> audienceParam = parameters.get(SyncConstants.WS_HEADER_AUDIENCE);
	    
	    if(CollectionUtils.isNotEmpty(tokenParam)) {
			return new JwtAuthenToken( tokenParam.get(0));
	    }
	    else if(CollectionUtils.isNotEmpty(passParam) && CollectionUtils.isNotEmpty(loginParam)) {
	    		return new UserPasswordAuthenToken( loginParam.get(0), passParam.get(0) );
	    }
	    
	    return null;
    }
    
    /**
     * parse the principal from the Http headers
     **/
    private Principal parseFromHeader(HttpHeaders headers) {
    	
    		String mapHeaders = headers.toSingleValueMap().toString();
		if(LOGGER.isDebugEnabled()) {
			LOGGER.info("determineUser headers : {}", mapHeaders);
		}
		String login = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_USERNAME);
		String passcode = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_PASSWORD);
		String token = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_TOKEN);
		//String audience = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_AUDIENCE);

	    if(StringUtils.isNotEmpty(token)) {
			return new JwtAuthenToken( token);
	    }
	    else if(StringUtils.isNotEmpty(passcode) && StringUtils.isNotEmpty(login)) {
	    		return new UserPasswordAuthenToken( login, passcode );
	    }
	    
	    return null;
	}
}
