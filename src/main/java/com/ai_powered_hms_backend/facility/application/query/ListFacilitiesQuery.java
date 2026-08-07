package com.ai_powered_hms_backend.facility.application.query;

import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;

public record ListFacilitiesQuery(
		FacilityStatus status, FacilityType type, int page, int size
		) {
	public ListFacilitiesQuery{
		if(page < 0) throw new IllegalArgumentException("Page must not be negative");
		if(size < 1 || size > 100) throw new IllegalArgumentException("Size must be between 1 and 100");
	}
}
