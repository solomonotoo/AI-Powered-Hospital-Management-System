package com.ai_powered_hms_backend.staff.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.application.api.StaffLookup;
import com.ai_powered_hms_backend.staff.application.api.StaffLookupSummary;
import com.ai_powered_hms_backend.staff.application.port.out.StaffRepository;
import com.ai_powered_hms_backend.staff.domain.model.StaffProfile;

@Service
public class StaffLookupService implements StaffLookup {
	
	private final StaffRepository staffRepository;
	
	public StaffLookupService(StaffRepository staffRepository) {
		super();
		this.staffRepository = staffRepository;
	}


	@Override
	public StaffLookupSummary getById(UUID staffId) {
		//find staff by id
		StaffProfile staff = staffRepository.findById(StaffId.of(staffId))
                .orElseThrow(() -> new IllegalArgumentException("No staff found with id " + staffId));
				
		return new StaffLookupSummary(staff.staffId().value(),
				staff.fullName().firstName() + " " + staff.fullName().lastName(),
				staff.role().name(), 
				staff.canAuthenticate()
			);
	}

}
