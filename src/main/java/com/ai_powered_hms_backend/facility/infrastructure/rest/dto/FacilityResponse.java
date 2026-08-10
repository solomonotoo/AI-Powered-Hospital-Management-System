package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

public record FacilityResponse(
		
		String facilityId,
		String code,
		String name,
		String type,
		FacilityAddressResponse location,
		String contactPhone,
		String contactEmail,
		String status,
		String createdAt,
		String updateAt
		) {

}
