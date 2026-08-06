package com.ai_powered_hms_backend.identity.domain.valueobjects;

import java.util.Objects;

public record HashedPassword(String value) {

//	compact canonical constructor of the HashedPassword record. 
//	It validates the constructor parameter before the record component is assigned.
	public HashedPassword{
		Objects.requireNonNull(value,"Password hash cannot be null");
		if(value.isBlank()) throw new IllegalArgumentException("Password hash cannot be blank");
	}
	
//	The compiler automatically treats it as if you had written:
//
//		public record HashedPassword(String value) {
//
//		    public HashedPassword(String value) {
//		        Objects.requireNonNull(value, "Password hash cannot be null");
//		        if (value.isBlank()) {
//		            throw new IllegalArgumentException("Password hash cannot be blank");
//		        }
//		        this.value = value;
//		    }
//		}
	
}
