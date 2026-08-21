package com.ai_powered_hms_backend.staff.application.port.in;

import com.ai_powered_hms_backend.staff.application.query.StaffSummaryResult;

public interface GetStaffSummaryUseCase {
	StaffSummaryResult getSummary();
}
