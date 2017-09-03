package com.gp.sync.web.socket;

import java.security.Principal;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gp.sync.web.model.ChatMessage;
import com.gp.sync.web.model.Greeting;
import com.gp.sync.web.model.HelloMessage;

@Controller
public class DevTestController {
	
	Logger log = LoggerFactory.getLogger(DevTestController.class);
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	SyncNodeSessionRegistry nodeSessionRegistry;
	
	/**
	 * Message be broadcast to all clients that subscribe the '/topic/greetings' 
	 **/
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message, Principal principal) throws Exception {
        log.info("Receive hello: {} - princ: {}", message.getName(), principal.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

	/**
	 * Message be feedback to client which send message, it must subscribe the /user/queue/notifications
	 **/
    @MessageMapping("/spittle")  
    @SendToUser("/queue/notifications")  
    public Greeting handleSpittle(HelloMessage message, Principal principal) {  
	    
	    log.info("Receive spittle: {} - princ: {}", message.getName(), principal.getName());
	    return new Greeting("Spittle, " + message.getName() + " from "+ principal.getName());  
    }  
    
    @MessageMapping("/chat")
    public void handleChat(ChatMessage message, Principal principal) {  
	    
	    log.info("Receive chat target: {} - princ: {}", message.getTarget(), principal.getName());
	    Greeting greeting = new Greeting("Chat, " + message.getMessage() + " from "+ principal.getName());  
	    template.convertAndSendToUser( message.getTarget(), "/queue/chat",  greeting ); 
    } 
    
    @MessageMapping("/test")
    @SendTo("/topic/greetings")
    public Greeting test(Message<?> message) throws Exception {
    		
    		byte[] bytes = (byte[]) message.getPayload();
    		String str = new String(bytes ,"UTF-8");
    		log.debug("msg: {} " , str);
    		
        return new Greeting("Hello, "+str+"!");
    }
    
    /**
	 * Message be broadcast to all clients that subscribe the '/topic/greetings' 
	 **/
    @MessageMapping("/test.sayhi")
    @SendTo("/topic/greetings")
    public Greeting sayhi(HelloMessage message, Principal principal) throws Exception {
        log.info("Receive sayhi: {} - princ: {}", message.getName(), principal.getName());
        return new Greeting("Hi, " + message.getName() + "!");
    }

    @MessageMapping("/test.sayhi.{username}")
    @SendTo("/topic/greetings")
    public Greeting sayhai2(HelloMessage message, Principal principal,@DestinationVariable("username") String username) throws Exception {
        log.info("Receive sayhi: {} - princ: {}", message.getName(), principal.getName());
        log.info("Receive sayhi: param is {} ", username);
        return new Greeting("Hi, prove the param in path" + message.getName() + "!");
    }
    
    @RequestMapping(path="/greetings", method=RequestMethod.POST)
    public void greet(String greeting) {
        String text = "[" + System.currentTimeMillis() + "]:" + greeting;
        this.template.convertAndSend("/topic/greetings", text);
    }
    
    @RequestMapping(path="/gpapi/all-users")
    @ResponseBody
    public Collection<String> allUsers() {
    		return nodeSessionRegistry.allKeys();
    }
}
