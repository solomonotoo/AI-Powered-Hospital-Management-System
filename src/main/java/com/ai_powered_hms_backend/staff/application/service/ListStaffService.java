package com.ai_powered_hms_backend.staff.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.staff.application.port.in.ListStaffUseCase;
import com.ai_powered_hms_backend.staff.application.port.out.StaffPage;
import com.ai_powered_hms_backend.staff.application.port.out.StaffRepository;
import com.ai_powered_hms_backend.staff.application.query.ListStaffQuery;

@Service
public class ListStaffService implements ListStaffUseCase{

	private final StaffRepository staffRepository;
	
	public ListStaffService(StaffRepository staffRepository) {
		super();
		this.staffRepository = staffRepository;
	}



	@Override
	@Transactional
	public StaffPage list(ListStaffQuery query) {
		// TODO Auto-generated method stub
		return staffRepository.findAll(query.role(), query.page(), query.size());
	}

}
