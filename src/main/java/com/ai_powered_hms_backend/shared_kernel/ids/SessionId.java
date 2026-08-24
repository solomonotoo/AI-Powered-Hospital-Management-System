package com.ai_powered_hms_backend.shared_kernel.ids;

import java.util.Objects;
import java.util.UUID;

public record SessionId(UUID value) {

	public SessionId{
		Objects.requireNonNull(value,"SessionId value must not be null");
	}
	
	public static SessionId newId() {
		return new SessionId(UUID.randomUUID());
	}
	
	public static SessionId of(UUID value) {
		return new SessionId(value);
	}
	
	//It makes converting database or API values much easier.
	public static SessionId of(String value) {
		return new SessionId(UUID.fromString(value));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value.toString();
	}
}
