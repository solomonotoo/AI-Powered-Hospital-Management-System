package com.ai_powered_hms_backend.identity.infrastructure.security;


import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ai_powered_hms_backend.identity.application.port.out.JwtTokenService;
import com.ai_powered_hms_backend.identity.application.port.out.TokenClaims;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.AuthenticatedPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	 private final JwtTokenService jwtTokenService;

	    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
	        this.jwtTokenService = jwtTokenService;
	    }

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	            throws ServletException, IOException {

	        String header = request.getHeader("Authorization");

	        if (header != null && header.startsWith("Bearer ")) {
	            try {
	               TokenClaims claims = jwtTokenService.parse(header.substring(7));

	                AuthenticatedPrincipal principal = new AuthenticatedPrincipal(claims.staffId(), claims.role());

	                var authentication = new UsernamePasswordAuthenticationToken(
	                        principal, null, List.of(new SimpleGrantedAuthority("ROLE_" + claims.role()))
	                );
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            } catch (Exception e) {
	                SecurityContextHolder.clearContext();
	            }
	        }

	        chain.doFilter(request, response);
	    }
}
