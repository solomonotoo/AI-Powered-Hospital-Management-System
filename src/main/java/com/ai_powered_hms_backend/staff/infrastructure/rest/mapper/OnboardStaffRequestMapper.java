package com.ai_powered_hms_backend.staff.infrastructure.rest.mapper;

import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;
import com.ai_powered_hms_backend.staff.application.command.OnboardStaffCommand;
import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;
import com.ai_powered_hms_backend.staff.domain.valueobjects.EmployeeNumber;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.OnboardStaffRequest;

public class OnboardStaffRequestMapper {

	public static OnboardStaffCommand toCommand(OnboardStaffRequest r, UUID createdBy) {
		return new OnboardStaffCommand(
				
				new EmployeeNumber(r.employeeNumbe()),
				new PersonName(r.firstName(), r.lastname()),
				StaffRole.valueOf(r.role().toUpperCase()),
				r.specialisation(),r.department(),new Email(r.workEmail()),
				r.phone() == null ? null : new PhoneNumber(r.phone()),
				r.licenseNumber(), r.qualifications(), r.joiningDate(),
				r.workingHours(), r.consultationFee(), createdBy
				);
	}
}
