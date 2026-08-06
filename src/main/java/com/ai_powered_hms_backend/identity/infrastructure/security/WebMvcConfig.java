package com.ai_powered_hms_backend.identity.infrastructure.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	
	private final HandlerMethodArgumentResolver currentUserArgumentResolver;

	public WebMvcConfig(HandlerMethodArgumentResolver currentUserArgumentResolver) {
	    this.currentUserArgumentResolver = currentUserArgumentResolver;
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(currentUserArgumentResolver);
	}
	
}
