package com.ai_powered_hms_backend.facility.application.port.in;

import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;

public interface ChangeFacilityStatusUseCase {
	void deactivate(FacilityId facilityId, UUID modifiedBy);
	void reactivate(FacilityId facilityId,UUID modifiedBy);
}
