package com.ai_powered_hms_backend.staff.domain.valueobjects;

import java.util.Objects;

public record EmployeeNumber(String value) {

	public EmployeeNumber{
		Objects.requireNonNull(value, "Employee number cannot be null");
		value = value.trim().toUpperCase();
		if(value.isEmpty() || value.length() > 30) {
			throw new IllegalArgumentException("Employee number muse be 1-3- characters");
		}
		
	}
	
	@Override
	public String toString() {
		return value;
	}
}
