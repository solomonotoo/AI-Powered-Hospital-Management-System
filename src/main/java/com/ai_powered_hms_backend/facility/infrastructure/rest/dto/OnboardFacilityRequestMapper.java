package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import java.util.UUID;

import com.ai_powered_hms_backend.facility.application.command.OnboardFacilityCommand;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.FacilityCode;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

public class OnboardFacilityRequestMapper {

	public static OnboardFacilityCommand toCommand(OnboardFacilityRequest request, UUID createdBy) {
		return new OnboardFacilityCommand(
				
				new FacilityCode(request.code()),
				request.name(),
				FacilityType.valueOf(request.type().toUpperCase()),
				Address.of(
						request.location().line1(),
						request.location().line2(),
						request.location().city(),
						request.location().stateOrRegion(),
						request.location().postalCode(),
						request.location().country()
						
						
						),
				PhoneNumber.of(request.contactPhone()),
				request.contactEmail() == null ? null : Email.of(request.contactEmail()),
				createdBy
				
				);
	}
}
