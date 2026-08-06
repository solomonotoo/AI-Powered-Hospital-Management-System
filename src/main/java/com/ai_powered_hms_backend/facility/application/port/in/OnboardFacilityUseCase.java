package com.ai_powered_hms_backend.facility.application.port.in;

import com.ai_powered_hms_backend.facility.application.command.OnboardFacilityCommand;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;

public interface OnboardFacilityUseCase {
	FacilityId onboard(OnboardFacilityCommand command);
}
