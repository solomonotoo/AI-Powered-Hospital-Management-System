package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import com.ai_powered_hms_backend.patient.domain.valueobjects.ConsentInformation;

public class ConsentInformationMapper {
	private ConsentInformationMapper() {
        // static utility class
    }
	public static ConsentInformationEmbeddable toEmbeddable(ConsentInformation domain) {
		if(domain == null) return null;
		
		return new ConsentInformationEmbeddable(
				domain.hasTreatmentConsent(), 
				domain.hasDataSharingConsent(), 
				domain.getTreatmentConsentGivenAt(), 
				domain.getDataConsentGivenAt(), 
				domain.getUpdatedAt(), 
				domain.getUpdatedBy());
	}
	
	public static ConsentInformation toDomain(ConsentInformationEmbeddable embeddable) {
		if(embeddable == null) return null;
		
		return  ConsentInformation.rehydrate(
				embeddable.isConsentToTreat(),
				embeddable.isConsentToShareData(),
				embeddable.getTreatmentConsentGivenAt(),
				embeddable.getDataConsentGivenAt(),
				embeddable.getUpdatedAt(),
				embeddable.getUpdatedBy()
				);
	}
}
