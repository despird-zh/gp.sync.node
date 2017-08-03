package com.gp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.DispatcherServlet;

import com.gp.core.AppContextHelper;
import com.gp.sync.AppContextListener;
import com.gp.sync.web.socket.AgentSessionRegistry;
import com.gp.sync.CoreStarter;
import com.gp.web.servlet.ServiceFilter;

/**
 *
 */
@Configuration
@ImportResource({
		"classpath:/gpress-datasource.xml"
	})
@ComponentScan(basePackages = { 
		"com.gp.core",
		"com.gp.sync.svc",
		"com.gp.sync.web"
 })
public class RootConfigurer {
	
	@Bean
    public AgentSessionRegistry webAgentSessionRegistry(){
        return new AgentSessionRegistry();
    }
	
	@Bean
	public AppContextListener appContextListener() {
		return new AppContextListener();
	}
	
	@Bean
	@Order(1)
	public AppContextHelper appContextHelper() {
		return new AppContextHelper();
	}
	
	/**
	 * The CoreStart listener 
	 **/
	@Bean ServletListenerRegistrationBean<CoreStarter> coreStarterListener(){
		ServletListenerRegistrationBean<CoreStarter> listenerReg = new ServletListenerRegistrationBean<CoreStarter>();
		
		listenerReg.setListener(new CoreStarter());
		return listenerReg;
	}
	
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader(ServiceFilter.AUTH_HEADER);
		config.addAllowedHeader("content-type");// required, otherwise the preflight not work
		config.addAllowedMethod("*");
		source.registerCorsConfiguration( ServiceFilter.FILTER_PREFIX + "/**", config);
        
		FilterRegistrationBean bean = new FilterRegistrationBean(new ServiceFilter(source));
		
		List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add(ServiceFilter.FILTER_PREFIX + "/*");
        
        bean.setUrlPatterns(urlPatterns);
		bean.setOrder(2);
		
		return bean;
	}
	
	@Bean
	public DispatcherServlet dispatcherServlet() {

		 // Create ApplicationContext
        AnnotationConfigWebApplicationContext webMvcContext = new AnnotationConfigWebApplicationContext();
        webMvcContext.register(WebMVCConfigurer.class);

	    DispatcherServlet servlet=new DispatcherServlet(webMvcContext);
 
	    return  servlet;
	}
	
    /**
     * Prepare the rest template for Jedis data  
     **/
    @Bean
    public JedisConnectionFactory connectionFactory() {
    	
    		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

    		connectionFactory.setHostName("127.0.0.1");
    		connectionFactory.setPort(6379);
    		return connectionFactory;
    }
    
    @Bean
	public RedisTemplate<String, Object> redisTemplate() {

    		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
    		redisTemplate.setConnectionFactory(connectionFactory());
    		redisTemplate.setKeySerializer(new StringRedisSerializer());
    		
    		return redisTemplate;
	}
    
    /**
     * Prepare the rest template for http json data requesting 
     **/
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }
    
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//ms
        factory.setConnectTimeout(15000);//ms
        return factory;
    }
}
