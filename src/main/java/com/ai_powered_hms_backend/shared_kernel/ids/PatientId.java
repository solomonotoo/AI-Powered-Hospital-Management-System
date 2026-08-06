package com.ai_powered_hms_backend.shared_kernel.ids;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public record PatientId(UUID value) {

	public PatientId{
		Objects.requireNonNull(value, "PatientId value must not be null");
	}
	
	public static PatientId newId() {
		return new PatientId(UUID.randomUUID());
	}
	
	public static PatientId of(UUID value) {
		return new PatientId(value);
	}
	
	public static PatientId of(String value) {
		 Objects.requireNonNull(
	                value,
	                "PatientId value must not be null"
	        );

		return new PatientId(UUID.fromString(value));
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
