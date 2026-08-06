package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import com.ai_powered_hms_backend.patient.domain.valueobjects.InsuranceInformation;

//convert InsuranceInformationEmbeddable to InsuranceInformation and vice versa
public class InsuranceInformationMapper {

	public static InsuranceInformationEmbeddable toEmbeddable(InsuranceInformation domain) {
		if(domain == null) return null;
		return new InsuranceInformationEmbeddable(domain.provider(),domain.policyNumber(),domain.groupNumber(),
				domain.coverageStartDate(),domain.expirationDate());
		
	}
	
	public static InsuranceInformation toDomaIn(InsuranceInformationEmbeddable embeddable) {
		if(embeddable == null) return null;
		return new InsuranceInformation(embeddable.getProvider(), embeddable.getPolicyNumber(),
				embeddable.getGroupNumber(), embeddable.getCoverageStartDate(), embeddable.getExpirationDate());
	}
}
