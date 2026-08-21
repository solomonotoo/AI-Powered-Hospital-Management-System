package com.ai_powered_hms_backend.staff.infrastructure.rest.dto;

import com.ai_powered_hms_backend.staff.application.query.StaffSummaryResult;

public class StaffSummaryResponseMapper {

	public static StaffSummaryResponse toResponse(StaffSummaryResult result) {
		return new StaffSummaryResponse(
				result.totalStaff(), 
				result.activeStaff(), 
				result.inactiveStaff(),
				result.onDuty(), 
				result.onLeave());
	}
}