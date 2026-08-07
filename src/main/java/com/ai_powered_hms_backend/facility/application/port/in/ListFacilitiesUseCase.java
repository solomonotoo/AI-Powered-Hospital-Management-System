package com.ai_powered_hms_backend.facility.application.port.in;

import com.ai_powered_hms_backend.facility.application.port.out.FacilityPage;
import com.ai_powered_hms_backend.facility.application.query.ListFacilitiesQuery;

public interface ListFacilitiesUseCase {
	FacilityPage list(ListFacilitiesQuery query);
}
