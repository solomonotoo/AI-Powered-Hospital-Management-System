package com.ai_powered_hms_backend.identity.application.port.out;

import com.ai_powered_hms_backend.identity.domain.valueobjects.HashedPassword;

public interface PasswordHasher {
	HashedPassword hash(String rawPassword);
	boolean matches(String rawPassword, HashedPassword hashedPassword);
}
