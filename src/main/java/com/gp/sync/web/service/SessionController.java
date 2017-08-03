package com.gp.sync.web.service;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/session")
public class SessionController {
 
    @RequestMapping(value="test", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String signin() {
  
        return "success";
    }
}