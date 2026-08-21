package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import java.util.Map;
import java.util.stream.Collectors;

import com.ai_powered_hms_backend.facility.application.query.FacilitySummaryResult;

public class FacilitySummaryResponseMapper {

	public static FacilitySummaryResponse toResponse(FacilitySummaryResult result) {
		Map<String, Long> byType = result.countByType().entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry :: getValue));
		
		return new FacilitySummaryResponse(
				result.totalFacilities(), result.activeFacilities(),
				result.inactiveFacilities(),result.pendingFacilities(),
				byType
				);
	}
}
