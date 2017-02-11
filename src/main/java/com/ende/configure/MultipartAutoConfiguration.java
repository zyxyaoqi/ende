package com.ende.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class MultipartAutoConfiguration  {

    @Bean 
    public StandardServletMultipartResolver  filterMultipartResolver() {
    	StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        multipartResolver.setResolveLazily(false);
        return multipartResolver;
    }        
}