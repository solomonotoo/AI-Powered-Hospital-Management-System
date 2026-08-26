package com.ai_powered_hms_backend.identity.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.port.out.UserActivityRepository;
import com.ai_powered_hms_backend.identity.application.port.out.UserActivityRepository.UserActivityRecord;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

@Service
public class UserActivityQueryService {

	private final UserActivityRepository activityRepository;

	public UserActivityQueryService(UserActivityRepository activityRepository) {
		super();
		this.activityRepository = activityRepository;
	}
	
	@Transactional
	public List<UserActivityRecord> listForUser(StaffId staffId){
		return activityRepository.findByStaffId(staffId);
	}
}
