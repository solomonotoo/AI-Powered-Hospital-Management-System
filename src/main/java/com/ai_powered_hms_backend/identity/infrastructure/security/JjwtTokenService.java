package com.ai_powered_hms_backend.identity.infrastructure.security;

import java.nio.charset.StandardCharsets;
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

	private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_TYPE = "type";

    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";
    private static final String CLAIM_SESSION_ID = "sid";
    
	private final SecretKey key;
	private final long accessTokenExpirationMinutes;
	private final long refreshTokenExpirationDays;
	public JjwtTokenService(
			@Value("${app.security.jwt.secret}") String secret, 
			@Value("${app.security.jwt.access-token.expiration-minutes:15}") long accessTokenExpirationMinutes,
			 @Value("${app.security.jwt.refresh-token.expiration-days:30}")
	        long refreshTokenExpirationDays) {
		super();
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
		this.refreshTokenExpirationDays = refreshTokenExpirationDays;
	}
	
	
//Embed a sessionId claim in both tokens at issuance. The access token stays 
//short-lived and mostly stateless (no DB hit needed for normal expiry), but 
//JwtAuthenticationFilter can still check sessionId against a revocation 
//flag — so DELETE /sessions/{sessionId} takes effect immediately on the next request,
//rather than waiting up to 15 minutes for the access token to naturally expire.
	
	@Override
	public IssuedToken issueAccessToken(StaffId staffId, String role, UUID sessionId) {
		Instant now = Instant.now();
		Instant expiry = now.plus(accessTokenExpirationMinutes, ChronoUnit.MINUTES);
		
		String token = Jwts.builder()
				.subject(staffId.value().toString())
				.claim(CLAIM_ROLE, role)
				.claim(CLAIM_TYPE, ACCESS_TOKEN)
				.claim(CLAIM_SESSION_ID, sessionId.toString())
				.issuedAt(Date.from(now))
				.expiration(Date.from(expiry))
				.signWith(key)
				.compact();
				
		return new IssuedToken(token,expiry,sessionId);
	}
	
	@Override
    public IssuedToken issueRefreshToken(
            StaffId staffId,
            String role, UUID sessionId) {

        Instant now = Instant.now();

        Instant expiry = now.plus(
                refreshTokenExpirationDays,
                ChronoUnit.DAYS
        );

        String token = Jwts.builder()
                .subject(staffId.value().toString())
                .claim(CLAIM_ROLE, role)
                .claim(CLAIM_TYPE, REFRESH_TOKEN)
                .claim(CLAIM_SESSION_ID, sessionId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(key)
                .compact();

        return new IssuedToken(token, expiry,sessionId);
    }
	
//	@Override
//	public TokenClaims parse(String token) {
//		Claims claims = Jwts.parser().verifyWith(key).build()
//				.parseSignedClaims(token).getPayload();
//		
//		return new TokenClaims(
//				StaffId.of(UUID.fromString(claims.getSubject())),
//				claims.get("role",String.class)
//				);
//	}
	
	 @Override
	    public TokenClaims parseAccessToken(String token) {

	        Claims claims = parse(token);

	        validateTokenType(claims, ACCESS_TOKEN);

	        return toTokenClaims(claims);
	    }
	
	 @Override
	    public TokenClaims parseRefreshToken(String token) {

	        Claims claims = parse(token);

	        validateTokenType(claims, REFRESH_TOKEN);

	        return toTokenClaims(claims);
	    }

	    private Claims parse(String token) {

	        return Jwts.parser()
	                .verifyWith(key)
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }

	private TokenClaims toTokenClaims(Claims claims) {

        return new TokenClaims(
                StaffId.of(
                        UUID.fromString(claims.getSubject())
                ),
                claims.get(CLAIM_ROLE, String.class),
                claims.get(CLAIM_TYPE, String.class),
                UUID.fromString(claims.get(CLAIM_SESSION_ID, String.class))
        );
    }

    private void validateTokenType(
            Claims claims,
            String expectedType) {

        String actualType = claims.get(
                CLAIM_TYPE,
                String.class
        );

        if (!expectedType.equals(actualType)) {
            throw new IllegalArgumentException(
                    "Invalid token type"
            );
        }
    }
	
}
