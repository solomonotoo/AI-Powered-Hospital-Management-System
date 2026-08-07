package com.ai_powered_hms_backend.facility.application.command;

import java.util.UUID;

import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

public record UpdateFacilityCommand(
		FacilityId facilityId,
        String name,
        FacilityType type,
        Address location,
        PhoneNumber contactPhone,
        Email contactEmail, // nullable
        UUID modifiedBy
		) {

}
