package com.ai_powered_hms_backend.identity.application.port.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public interface UserActivityRepository {
	void record(StaffId staffId,String eventType, String description, UUID actorId);
	List<UserActivityRecord> findByStaffId(StaffId staffId);
	
	record UserActivityRecord(UUID id, String eventType, String description, LocalDateTime occuredAt) {}
}
