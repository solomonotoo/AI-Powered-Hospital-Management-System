package com.ai_powered_hms_backend.identity.application.port.in;

import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public interface SuspendedUserUseCase {
	void suspend(StaffId staffId,UUID suspendedBy);
	void reactivate(StaffId staffId,UUID reactivatedBy);
}
