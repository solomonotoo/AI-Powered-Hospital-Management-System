package com.ai_powered_hms_backend.shared_kernel.valueobjects;

import java.util.Objects;

import jakarta.persistence.Embeddable;

//@Embeddable
public record Address(
		String line1,
		String line2,
        String city,
        String state,
        String postalCode,
        String country
) {

	public static Address of(String line1,
			String line2,
	        String city,
	        String state,
	        String postalCode,
	        String country) {
		return new Address(line1, line2, city, state, postalCode, country);
	}
	
	
    public Address {
    	line1 = requireNotBlank(line1, "Line 1");
        city = requireNotBlank(city, "City");
        country = requireNotBlank(country, "Country");
        
     
     // Clean optional fields: convert blanks/spaces to null or empty strings
        line2 = (line2 == null) ? "" : line2.trim();
        state = (state == null) ? "" : state.trim();
        postalCode = (postalCode == null) ? "" : postalCode.trim();
    }

    private static String requireNotBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " cannot be null");

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return trimmed;
    }

//    public String formatted() {
//    	  // Formats cleanly even if state or postal code are empty
//        return String.format("%s, %s, %s %s, %s, %s", 
//            line1,line2, city, state, postalCode, country).replaceAll(", ,", ",");
//    }
    
    public String formatted() {
        StringBuilder sb = new StringBuilder(line1);
        if (!line2.isBlank()) sb.append(", ").append(line2);
        sb.append(", ").append(city);
        if (!state.isBlank()) sb.append(", ").append(state);
        if (!postalCode.isBlank()) sb.append(" ").append(postalCode);
        sb.append(", ").append(country);
        return sb.toString();
    }
}