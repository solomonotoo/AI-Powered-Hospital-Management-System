package com.ai_powered_hms_backend.shared_kernel.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

//@Embeddable
public record PhoneNumber(String value) {

	private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[1-9]\\d{1,14}$"); // E.164 format
	
	public static PhoneNumber of(String value) {
		return new PhoneNumber(value);
	}

    public PhoneNumber {
        Objects.requireNonNull(value, "Phone number cannot be null");

        value = value.trim();

        if (!PHONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "Invalid phone number. Expected E.164 format (e.g. +14155552671)"
            );
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
