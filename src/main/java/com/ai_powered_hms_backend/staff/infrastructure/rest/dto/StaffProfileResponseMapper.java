package com.ai_powered_hms_backend.staff.infrastructure.rest.dto;

import com.ai_powered_hms_backend.staff.domain.model.StaffProfile;

public class StaffProfileResponseMapper {

	public static StaffProfileResponse toResponse(StaffProfile staffProfile) {
		return new StaffProfileResponse(staffProfile.staffId().value().toString(),

				staffProfile.employeeNumber().value(),
				//NB string fullName.toString() changed to fullName.firstName and fullName.lastName in order not to leak the domain
				staffProfile.fullName().firstName(),
				staffProfile.fullName().lastName(),
				staffProfile.role().name(),

				staffProfile.specialisation(),

				staffProfile.department(),

				staffProfile.workEmail() == null ? null : staffProfile.workEmail().getValue(),

				staffProfile.phone() == null ? null : staffProfile.phone().value(),

				staffProfile.licenseNumber(),

				staffProfile.qualifications(),

				staffProfile.joiningDate(),

				staffProfile.endDate(),

				staffProfile.workingHours(),

				staffProfile.consultationFee(),

				staffProfile.status().name(),

				staffProfile.audit().getCreatedAt().toString(),

				staffProfile.audit().getUpdatedAt() == null ? null : staffProfile.audit().getUpdatedAt().toString()

		);
	}
}
