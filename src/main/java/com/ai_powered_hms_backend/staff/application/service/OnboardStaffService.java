package com.ai_powered_hms_backend.staff.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.application.command.OnboardStaffCommand;
import com.ai_powered_hms_backend.staff.application.port.in.OnboardStaffUseCase;
import com.ai_powered_hms_backend.staff.application.port.out.StaffRepository;
import com.ai_powered_hms_backend.staff.domain.model.StaffProfile;

@Service
public class OnboardStaffService implements OnboardStaffUseCase {

	private final StaffRepository staffRepository;
	
	public OnboardStaffService(StaffRepository staffRepository) {
		super();
		this.staffRepository = staffRepository;
	}



	@Override
	@Transactional
	public StaffId onboard(OnboardStaffCommand command) {
		
		if(staffRepository.existsByEmployeeNumber(command.employeeNumber())) {
			throw new DuplicateStaffException(
					"Employee number already exists: " + command.employeeNumber()
					);
		}
		
		if(staffRepository.existsByWorkEmail(command.workEmail().getValue())) {
			throw new DuplicateStaffException( "Work email already in use: " + command.workEmail());
		}
		
		StaffProfile staff = StaffProfile.onboard(
				command.employeeNumber(), command.fullName(), command.role(),
				command.specialisation(), command.department(), command.workEmail(),
				command.phone(), command.licenseNumber(), command.qualifications(),
				command.joiningDate(), command.workingHours(), command.consultationFee(),
				command.createdBy()
				
				
		);
		
		staffRepository.save(staff);
		
		return staff.staffId();
	}

}
