//package com.hms_application.patient.infrastructure.persistence;
//
//import com.hms_application.patient.domain.enums.Religion;
//import com.hms_application.patient.domain.valueobjects.DateOfBirth;
//import com.hms_application.shared_kernel.enums.Gender;
//import com.hms_application.shared_kernel.enums.MaritalStatus;
//import com.hms_application.shared_kernel.infrastructure.persistence.PersonNameEmbeddable;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//import jakarta.persistence.Embedded;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//
//@Embeddable
//public class PersonalDetailsEmbeddable {
//	@Embedded
//	private PersonNameEmbeddable fullName;
//
//	@Enumerated(EnumType.STRING)
//	@Column(name = "gender", length = 20)
//	private Gender gender;
//
//	@Enumerated(EnumType.STRING)
//	@Column(name = "marital_status", length = 20)
//	private MaritalStatus maritalStatus;
//
//	
//	private DateOfBirth dateOfBirth;
//
//	@Enumerated(EnumType.STRING)
//	@Column(name = "religion", length = 30)
//	private Religion religion;
//
//	@Column(name = "nationality", length = 100)
//	private String nationality;
//
//	@Column(name = "ethnicity", length = 100)
//	private String ethnicity;
//
//	@Column(name = "occupation", length = 150)
//	private String occupation;
//
//	protected PersonalDetailsEmbeddable() {
//		// JPA
//	}
//
//	public PersonalDetailsEmbeddable(
//			PersonNameEmbeddable fullName, 
//			Gender gender, 
//			MaritalStatus maritalStatus,
//			DateOfBirth dateOfBirth, 
//			Religion religion, 
//			String nationality, 
//			String ethnicity, 
//			String occupation) {
//		this.fullName = fullName;
//		this.gender = gender;
//		this.maritalStatus = maritalStatus;
//		this.dateOfBirth = dateOfBirth;
//		this.religion = religion;
//		this.nationality = nationality;
//		this.ethnicity = ethnicity;
//		this.occupation = occupation;
//	}
//
//	public PersonNameEmbeddable getFullName() {
//		return fullName;
//	}
//
//	public Gender getGender() {
//		return gender;
//	}
//
//	public MaritalStatus getMaritalStatus() {
//		return maritalStatus;
//	}
//
//	public DateOfBirth getDateOfBirth() {
//		return dateOfBirth;
//	}
//
//	public Religion getReligion() {
//		return religion;
//	}
//
//	public String getNationality() {
//		return nationality;
//	}
//
//	public String getEthnicity() {
//		return ethnicity;
//	}
//
//	public String getOccupation() {
//		return occupation;
//	}
//
//}
