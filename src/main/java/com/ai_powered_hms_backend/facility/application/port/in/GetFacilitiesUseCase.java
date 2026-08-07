package com.ai_powered_hms_backend.facility.application.port.in;

import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;

public interface GetFacilitiesUseCase {
	Facility getById(FacilityId id);
}
