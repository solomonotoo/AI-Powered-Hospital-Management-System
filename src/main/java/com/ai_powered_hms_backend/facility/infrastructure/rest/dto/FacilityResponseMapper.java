package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import com.ai_powered_hms_backend.facility.domain.model.Facility;

//maps facility domain to response dto
public class FacilityResponseMapper {

	public static FacilityResponse toResponse(Facility facility) {
		return new FacilityResponse(
				
				facility.facilityId().value().toString(),
				facility.code().value(),
				facility.name(),
				facility.type().name(),
				new FacilityAddressResponse(facility.location().line1(), facility.location().line2(), facility.location().city(), facility.location().state(), facility.location().postalCode(), facility.location().country()),
				facility.contactPhone() == null ? null : facility.contactPhone().value(),
				facility.contactEmail() == null ? null : facility.contactEmail().getValue(),
				facility.status().name(),
				facility.audit().getCreatedAt().toString(),
				facility.audit().getUpdatedAt() == null ? null : facility.audit().getUpdatedAt().toString()
				);
	}
}
