package com.ai_powered_hms_backend.staff.infrastructure.persistence;

import com.ai_powered_hms_backend.staff.domain.enums.StaffStatus;

public interface StaffStatusCount {
	 StaffStatus getStatus();
	    Long getTotal();
}
