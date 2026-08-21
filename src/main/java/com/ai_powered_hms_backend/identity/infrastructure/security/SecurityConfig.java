package com.ai_powered_hms_backend.identity.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.Filter;

@Configuration
public class SecurityConfig {

	private final CorsConfigurationSource corsConfigurationSource;

	private final Filter jwtAuthenticationFilter;

	public SecurityConfig(Filter jwtAuthenticationFilter, CorsConfigurationSource corsConfigurationSource) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.corsConfigurationSource = corsConfigurationSource;
	}

//	private final JwtTokenService jwtTokenService;
//
//    public SecurityConfig(JwtTokenService jwtTokenService) {
//        this.jwtTokenService = jwtTokenService;
//    }

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Constructed directly — never registered as a Spring bean, so no
		// BeanPostProcessor (Modulith observability, Micrometer tracing, etc.)
		// can wrap it in a JDK dynamic proxy.
		// JwtAuthenticationFilter jwtAuthenticationFilter = new
		// JwtAuthenticationFilter(jwtTokenService);

		http.cors(cors -> cors.configurationSource(corsConfigurationSource)).csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
						.requestMatchers("/error").permitAll().anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	
}
