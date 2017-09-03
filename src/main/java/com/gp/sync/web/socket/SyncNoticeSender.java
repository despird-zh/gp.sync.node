package com.gp.sync.web.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gp.sync.web.model.Greeting;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class monitor the received message and analysis is it need to notify 
 * node to fetch the incremental sync message. 
 **/
@Component
public class SyncNoticeSender {
	
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    static Logger LOGGER = LoggerFactory.getLogger(SyncNoticeSender.class);
    
    @Autowired
    private SimpMessagingTemplate broker;

    @Autowired
    public SyncNoticeSender(final SimpMessagingTemplate broker) {
        this.broker = broker;
    }

    @Scheduled(fixedRate = 20000)
    public void run() {
       String time = LocalTime.now().format(TIME_FORMAT);

       //LOGGER.info("Time broadcast: {}", time);
       broker.convertAndSend("/topic/greetings", new Greeting("Current time is " + time));
    }
}
