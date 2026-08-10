package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

public record FacilityAddressResponse(
		String line1,String line2,String city,String state,String postalCode,String country
		) {

}
