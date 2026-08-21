package com.ai_powered_hms_backend.staff.application.service;

import org.springframework.stereotype.Service;

import com.ai_powered_hms_backend.staff.application.port.in.GetStaffSummaryUseCase;
import com.ai_powered_hms_backend.staff.application.port.out.StaffRepository;
import com.ai_powered_hms_backend.staff.application.query.StaffSummaryResult;

@Service
public class GetStaffSummaryService implements GetStaffSummaryUseCase{

	private final StaffRepository staffRepository;
	
	public GetStaffSummaryService(StaffRepository staffRepository) {
		super();
		this.staffRepository = staffRepository;
	}

	@Override
	public StaffSummaryResult getSummary() {
		// TODO Auto-generated method stub
		return staffRepository.getSummary();
	}

	
}
