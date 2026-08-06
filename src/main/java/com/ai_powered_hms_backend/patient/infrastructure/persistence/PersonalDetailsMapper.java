//package com.hms_application.patient.infrastructure.persistence;
//
//import com.hms_application.patient.domain.valueobjects.PersonalDetails;
//import com.hms_application.shared_kernel.infrastructure.persistence.PersonNameMapper;
//
//public class PersonalDetailsMapper {
//
//	public static PersonalDetailsEmbeddable toEmbeddable(PersonalDetails domain) {
//		if(domain == null) return null;
//		return new PersonalDetailsEmbeddable(
//				PersonNameMapper.toEmbeddable(domain.fullName()),
//				domain.gender(),
//				domain.maritalStatus(),
//				domain.dateOfBirth(),
//				domain.religion(),
//				domain.nationality(),
//				domain.ethnicity(),
//				domain.occupation()
//				
//				);
//	}
//	
//	public static PersonalDetails toDomain(PersonalDetailsEmbeddable embeddable) {
//		if(embeddable == null) return null;
//		
//		return new PersonalDetails(
//				PersonNameMapper.toDomain(embeddable.getFullName()), 
//				embeddable.getGender(), embeddable.getMaritalStatus(),
//				embeddable.getDateOfBirth(), embeddable.getReligion(),
//				embeddable.getNationality(), embeddable.getEthnicity(), embeddable.getOccupation());
//	}
//}
