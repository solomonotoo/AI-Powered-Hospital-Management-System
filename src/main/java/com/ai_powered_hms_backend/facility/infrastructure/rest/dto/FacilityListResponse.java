package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import java.util.List;

public record FacilityListResponse(
		List<FacilityResponse> facilities,
		long totalElements,
		int page,
		int size
		) {

}
