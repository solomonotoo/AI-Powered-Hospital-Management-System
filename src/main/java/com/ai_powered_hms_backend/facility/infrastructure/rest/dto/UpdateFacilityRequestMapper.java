package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import java.util.UUID;

import com.ai_powered_hms_backend.facility.application.command.UpdateFacilityCommand;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

public class UpdateFacilityRequestMapper {

	public static UpdateFacilityCommand toCommand(UUID facilityId, UpdateFacilityRequest request, UUID modifiedBy) {
		return new UpdateFacilityCommand(
				FacilityId.of(facilityId),
				request.name(),
				FacilityType.valueOf(request.type().toUpperCase()),
				new Address(
						request.location().line1(), request.location().line2(),
						request.location().city(), request.location().stateOrRegion(),
						request.location().postalCode(), request.location().city()
						),
				new PhoneNumber(request.contactPhone()),
				request.contactEmail() == null ? null : new Email(request.contactEmail()),
						modifiedBy
				
				);
	}
}
