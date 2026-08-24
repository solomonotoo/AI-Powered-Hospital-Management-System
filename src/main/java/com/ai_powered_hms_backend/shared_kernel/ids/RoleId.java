package com.ai_powered_hms_backend.shared_kernel.ids;

import java.util.Objects;
import java.util.UUID;

public record RoleId(UUID value) {

	//Constructor validation This is called the compact constructor, 
		//and it's the recommended way to validate record components.
	public RoleId{
		Objects.requireNonNull(value, "RoleId value cannot be null");
	}
	
	public static RoleId newId() {
		return new RoleId(UUID.randomUUID());
	}
	
	public static RoleId of(UUID value) {
		return new RoleId(value);
	}
	
	
	//It makes converting database or API values much easier.
	public static RoleId of(String value) {
		return new RoleId(UUID.fromString(value));
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value.toString();
	}
}
