package com.gp.sync.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DemoController {

	
	private SimpMessagingTemplate template;
	
	@Autowired
    public DemoController(SimpMessagingTemplate template) {
        this.template = template;
    }
	
    @RequestMapping(path="/greetings", method=RequestMethod.POST)
    public void greet(String greeting) {
        String text = "[" + System.currentTimeMillis() + "]:" + greeting;
        this.template.convertAndSend("/topic/greetings", text);
    }
}
