package com.gp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ 
	RootConfigurer.class, 
	ServiceConfigurer.class,
	StompBrokerConfig.class
	})

public class GSyncServerApp{

    /**
	 * The main entrance of application 
	 **/
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GSyncServerApp.class);
		//app.addListeners(new AppContextListener());
        app.run( args);
    }

}
