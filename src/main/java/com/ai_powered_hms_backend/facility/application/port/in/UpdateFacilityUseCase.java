package com.ai_powered_hms_backend.facility.application.port.in;

import com.ai_powered_hms_backend.facility.application.command.UpdateFacilityCommand;

public interface UpdateFacilityUseCase {
	void update(UpdateFacilityCommand command);
}
