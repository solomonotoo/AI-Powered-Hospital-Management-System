package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import java.util.Map;

public record FacilitySummaryResponse(
		long totalFacilities,
        long activeFacilities,
        long inactiveFacilities,
        long pendingFacilities,
        Map<String,Long> countType
		) {

}
