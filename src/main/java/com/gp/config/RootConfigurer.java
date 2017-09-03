package com.gp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.gp.core.AppContextHelper;
import com.gp.sync.AppContextListener;
import com.gp.sync.web.socket.SyncNodeSessionRegistry;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + ServiceConfigurer.SERVICE_PRECEDENCE + 10)
@ImportResource({
		"classpath:/gpress-datasource.xml"
	})
@ComponentScan(basePackages = { 
		"com.gp.core",
		"com.gp.sync.dao",
		"com.gp.sync.svc"
 })
public class RootConfigurer {
	
	@Bean
    public SyncNodeSessionRegistry webAgentSessionRegistry(){
        return new SyncNodeSessionRegistry();
    }
	
	/**
	 * Trigger the AppContext event 
	 **/
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
	 * Build the service filter bean, it filter out the valid request to /gpapi/* service.
	 * e.g. authenticate.do to fetch a valid token 
	 **/
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(false);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader(ServiceFilter.AUTH_HEADER);
//		config.addAllowedHeader("content-type");// required, otherwise the preflight not work
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration( ServiceFilter.FILTER_PREFIX + "/**", config);
//        
//		FilterRegistrationBean bean = new FilterRegistrationBean(new ServiceFilter(source));
//		
//		List<String> urlPatterns = new ArrayList<String>();
//        urlPatterns.add(ServiceFilter.FILTER_PREFIX + "/*");
//        
//        bean.setUrlPatterns(urlPatterns);
//		bean.setOrder(2);
//		
//		return bean;
//	}
	
	/**
	 * Prepare the dispatch servlet 
	 **/
//	@Bean
//	public DispatcherServlet dispatcherServlet() {
//
//		 // Create ApplicationContext
//        AnnotationConfigWebApplicationContext webMvcContext = new AnnotationConfigWebApplicationContext();
//        webMvcContext.register(WebMVCConfigurer.class);
//
//	    DispatcherServlet servlet=new DispatcherServlet(webMvcContext);
// 
//	    return  servlet;
//	}
	
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
