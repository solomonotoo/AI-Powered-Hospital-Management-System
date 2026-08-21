package com.ai_powered_hms_backend.facility.application.query;

import java.util.Map;

import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;

//for summary cards in the ui
public record FacilitySummaryResult(
		long totalFacilities,
        long activeFacilities,
        long inactiveFacilities,
        long pendingFacilities,
        Map<FacilityType, Long> countByType
		) {

}
