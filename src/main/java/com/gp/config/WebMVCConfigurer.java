package com.gp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.gp.web.DatabaseMessageSource;
import com.gp.web.PrincipalLocaleResolver;

@EnableWebMvc
public class WebMVCConfigurer extends WebMvcConfigurerAdapter {
	
	/**
	 * Create locale resolver to extract locale from request. 
	 **/
	@Bean
	public LocaleResolver localeResolver() {
	    return new PrincipalLocaleResolver();
	}
	
	/**
	 * Create the message source to inject it into Controller. 
	 **/
    @Bean
    public MessageSource messageSource() {
    	
        DatabaseMessageSource source = new DatabaseMessageSource();
        return source;
    }
    
}