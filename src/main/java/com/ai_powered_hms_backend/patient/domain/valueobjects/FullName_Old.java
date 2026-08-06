//package com.hms_application.patient.domain.valueobjects;
//
//import java.util.Objects;
//
//import com.hms_application.patient.domain.exceptions.InvalidNameException;
//
//import lombok.Getter;
//
//@Getter
////@EqualsAndHashCode
//public class FullName_Old {
//
//	private final String firstName;
//	private final String lastName;
//	private final String maidenName;
//	public FullName_Old(String firstName, String lastName, String maidenName) {
//		super();
//		this.firstName = validate(firstName, "First name");
//		this.lastName = validate(lastName, "Last name");
//		this.maidenName = (maidenName != null && !maidenName.isBlank() ?  validate(maidenName, "Maiden name") : null);
//	}
//	
//	
//	private String validate(String value, String field) {
//		    if (value == null) {
//		        throw new InvalidNameException(field + " cannot be null");
//		    }
//
//		    String trimmed = value.trim();
//
//		    if (trimmed.isEmpty()) {
//		        throw new InvalidNameException(field + " cannot be empty");
//		    }
//
//		    if (trimmed.length() < 2 || trimmed.length() > 45) {
//		        throw new InvalidNameException(field + " must be between 2 and 45 characters");
//		    }
//
//		    return trimmed;
//		
//	}
//	
//	public FullName_Old(String firstName,String lastName) {
//		this(firstName, lastName, null);
//	}
//
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(firstName, lastName, maidenName);
//	}
//
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		FullName_Old other = (FullName_Old) obj;
//		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
//				&& Objects.equals(maidenName, other.maidenName);
//	}
//
//	
//	
//}