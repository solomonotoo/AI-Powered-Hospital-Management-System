package com.ai_powered_hms_backend.staff.application.port.in;

import com.ai_powered_hms_backend.staff.application.port.out.StaffPage;
import com.ai_powered_hms_backend.staff.application.query.ListStaffQuery;

public interface ListStaffUseCase {
	StaffPage list(ListStaffQuery query);
}
