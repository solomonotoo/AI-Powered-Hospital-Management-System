package com.ai_powered_hms_backend.shared_kernel.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

import com.ai_powered_hms_backend.shared_kernel.exceptions.InvalidEmailException;

import jakarta.persistence.Embeddable;

//@Embeddable
public final class Email {

	private static final Pattern EMAIL_PATTERN =         Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	private final String value;
	
	public static Email of(String value) {
		return new Email(value);
	}
	
    public Email(String value) {
        if (value == null || value.isBlank())
            throw new InvalidEmailException("Email cannot be null or empty");
        String n = value.toLowerCase().trim();
        if (!EMAIL_PATTERN.matcher(n).matches())
            throw new InvalidEmailException("Invalid email format: " + value);
        this.value = n;
    }
    
    public String getValue() { return value; }
    
    @Override 
    public boolean equals(Object o) {
        if (!(o instanceof Email e)) return false;
        return Objects.equals(value, e.value);
    }
    
    @Override 
    public int hashCode() { return Objects.hash(value); }
    
    @Override 
    public String toString() { return value; }

}
