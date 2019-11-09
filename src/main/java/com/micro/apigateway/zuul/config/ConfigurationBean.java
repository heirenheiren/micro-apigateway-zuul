package com.micro.apigateway.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.micro.apigateway.zuul.filter.AuthorizedRequestFilter;

@Configuration
public class ConfigurationBean {
	@Bean
    public AuthorizedRequestFilter getAuthorizedRequestFilter() {
        return new AuthorizedRequestFilter();
    }
}
