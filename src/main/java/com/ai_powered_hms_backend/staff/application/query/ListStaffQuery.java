package com.ai_powered_hms_backend.staff.application.query;

import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;

public record ListStaffQuery(
		StaffRole role,  int page, int size) {

	public ListStaffQuery{
		if(page < 0) throw new IllegalArgumentException("Page must not be negative");
		if(size < 1 || size > 100) throw new IllegalArgumentException("Size must be between 1 and 100");
	}
}
