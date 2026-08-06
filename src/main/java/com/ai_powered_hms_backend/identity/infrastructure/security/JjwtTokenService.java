package com.ai_powered_hms_backend.identity.infrastructure.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.identity.application.port.out.IssuedToken;
import com.ai_powered_hms_backend.identity.application.port.out.JwtTokenService;
import com.ai_powered_hms_backend.identity.application.port.out.TokenClaims;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JjwtTokenService implements JwtTokenService{

	private final SecretKey key;
	private final long expirationMinutes;
	public JjwtTokenService(
			@Value("${app.security.jwt.secret}") String secret, 
			@Value("${app.security.jwt.access-token.expiration-minutes:60}") long expirationMinutes) {
		super();
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.expirationMinutes = expirationMinutes;
	}
	
	@Override
	public IssuedToken issue(StaffId staffId, String role) {
		Instant now = Instant.now();
		Instant expiry = now.plus(expirationMinutes, ChronoUnit.MINUTES);
		
		String token = Jwts.builder()
				.subject(staffId.value().toString())
				.claim("role", role)
				.issuedAt(Date.from(now))
				.expiration(Date.from(expiry))
				.signWith(key)
				.compact();
				
		return new IssuedToken(token,expiry);
	}
	
	
	@Override
	public TokenClaims parse(String token) {
		Claims claims = Jwts.parser().verifyWith(key).build()
				.parseSignedClaims(token).getPayload();
		
		return new TokenClaims(
				StaffId.of(UUID.fromString(claims.getSubject())),
				claims.get("role",String.class)
				);
	}
	
	
}
