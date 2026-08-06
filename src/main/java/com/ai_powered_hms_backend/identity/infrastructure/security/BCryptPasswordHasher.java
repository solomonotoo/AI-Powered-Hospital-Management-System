package com.ai_powered_hms_backend.identity.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.identity.application.port.out.PasswordHasher;
import com.ai_powered_hms_backend.identity.domain.valueobjects.HashedPassword;

//passwoerd hashing (BCrypt)

@Component
public class BCryptPasswordHasher implements PasswordHasher {

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public HashedPassword hash(String rawPassword) {
		// TODO Auto-generated method stub
		return new HashedPassword(encoder.encode(rawPassword));
	}

	@Override
	public boolean matches(String rawPassword, HashedPassword hashedPassword) {
		// TODO Auto-generated method stub
		return encoder.matches(rawPassword, hashedPassword.value());
	}

}
