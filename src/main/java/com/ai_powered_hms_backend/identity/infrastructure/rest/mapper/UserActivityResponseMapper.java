package com.ai_powered_hms_backend.identity.infrastructure.rest.mapper;

import com.ai_powered_hms_backend.identity.application.port.out.UserActivityRepository.UserActivityRecord;
import com.ai_powered_hms_backend.identity.application.port.out.UserActivityRepository;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.UserActivityResponse;

public class UserActivityResponseMapper {

	public static UserActivityResponse toResponse(UserActivityRecord record) {
        return new UserActivityResponse(
                record.id().toString(), record.eventType(), record.description(), record.occuredAt().toString()
        );
    }
}
