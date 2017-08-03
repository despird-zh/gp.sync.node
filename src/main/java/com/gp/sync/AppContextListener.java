package com.gp.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class AppContextListener implements ApplicationListener<ContextRefreshedEvent> {

	static Logger LOGGER = LoggerFactory.getLogger(AppContextListener.class);
		
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
		// ignore
    		LOGGER.debug("AppContextListener initialized");
    }
}
