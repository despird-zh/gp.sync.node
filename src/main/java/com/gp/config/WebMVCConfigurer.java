package com.gp.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.gp.common.GPrincipal;
import com.gp.sync.CoreStarter;
import com.gp.web.DatabaseMessageSource;
import com.gp.web.PrincipalLocaleResolver;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + ServiceConfigurer.SERVICE_PRECEDENCE + 20)
@ComponentScan(basePackages = { 
		"com.gp.web.api",
		"com.gp.sync.web.api",
		"com.gp.sync.web.view" })
public class WebMVCConfigurer extends WebMvcConfigurerAdapter {

	/**
	 * The CoreStart listener, it starts the CoreEngine which detect and prepare the CoreInitializer via java serviceloader(SPI).
	 * assembly the initializer to sort the LifecycleHooker with priority. 
	 **/
	@Bean 
	ServletListenerRegistrationBean<CoreStarter> coreStarterListener(){
		ServletListenerRegistrationBean<CoreStarter> listenerReg = new ServletListenerRegistrationBean<CoreStarter>();
		
		listenerReg.setListener(new CoreStarter());
		return listenerReg;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	/**
	 * Create locale resolver to extract locale from request.
	 **/
	@Bean
	public LocaleResolver localeResolver() {
		return new SyncLocaleResolver();
	}

	/**
	 * Create the message source to inject it into Controller.
	 **/
	@Bean
	public MessageSource messageSource() {

		DatabaseMessageSource source = new DatabaseMessageSource();
		return source;
	}

	/**
	 * Inner class to override the default PrincipalLocaleResolver 
	 **/
	private class SyncLocaleResolver extends PrincipalLocaleResolver{
		
		@Override
		public Locale resolveLocale(HttpServletRequest request) {
			Authentication authen = SecurityContextHolder.getContext().getAuthentication();
			
			if(authen != null && authen.getDetails() != null && authen.getDetails() instanceof GPrincipal) {
				GPrincipal principal = (GPrincipal) authen.getDetails();
			
				setDefaultLocale(principal.getLocale());
				return getDefaultLocale();
			
			}
			return super.resolveLocale(request);
		}
		
	}
}