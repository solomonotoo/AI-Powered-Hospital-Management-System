package com.ai_powered_hms_backend.identity.infrastructure.security;


import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ai_powered_hms_backend.identity.application.port.out.JwtTokenService;
import com.ai_powered_hms_backend.identity.application.port.out.SessionRepository;
import com.ai_powered_hms_backend.identity.application.port.out.TokenClaims;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.AuthenticatedPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	 private final JwtTokenService jwtTokenService;
	 private final SessionRepository sessionRepository;
	    

	    public JwtAuthenticationFilter(JwtTokenService jwtTokenService, SessionRepository sessionRepository) {
		this.jwtTokenService = jwtTokenService;
		this.sessionRepository = sessionRepository;
	}

		@Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	            throws ServletException, IOException {
	    	// System.out.println(">>> JwtAuthenticationFilter.doFilterInternal CALLED for " + request.getRequestURI());

	        String header = request.getHeader("Authorization");

	        LOGGER.info("JwtAuthenticationFilter invoked for {} — Authorization header present: {}",
	                request.getRequestURI(), header != null);
	        
	        if (header != null && header.startsWith("Bearer ")) {
	            try {
	               TokenClaims claims = jwtTokenService.parseAccessToken(header.substring(7));

	               //check session validity, not just signature
	               if(!sessionRepository.isValid(claims.sessionId())) {
	            	LOGGER.warn("Rejected request - session {} is revoked or expired", claims.sessionId());   
	            	SecurityContextHolder.clearContext();
	            	chain.doFilter(request, response);
	            	return;
	               
	               }
	               
	               LOGGER.info("JWT parsed successfully — staffId={}, role={}", claims.staffId(), claims.role());

	                AuthenticatedPrincipal principal = new AuthenticatedPrincipal(claims.staffId(), claims.role());

	                var authentication = new UsernamePasswordAuthenticationToken(
	                        principal, null, List.of(new SimpleGrantedAuthority("ROLE_" + claims.role()))
	                );
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	          
	                LOGGER.info("Authentication set in SecurityContext with authority ROLE_{}", claims.role());
	                
	            
	            } catch (Exception e) {
	            	LOGGER.warn("JWT authentication failed: {}", request.getRequestURI(), e);
	                SecurityContextHolder.clearContext();
	            }
	        }else {
	        	 LOGGER.warn("No Bearer token found on request to {} — header value: [{}]",
	                     request.getRequestURI(), header);
			}

	        chain.doFilter(request, response);
	    }
	    
	    @Override
	    protected boolean shouldNotFilter(HttpServletRequest request) {

	        String path = request.getServletPath();

	        return path.startsWith("/swagger-ui/")
	                || path.startsWith("/api-docs")
	                || path.equals("/favicon.ico");
	    }
}
