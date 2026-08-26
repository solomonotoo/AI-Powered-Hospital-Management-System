package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

public record UserActivityResponse(
		String id,
		String eventType,
		String description,
		String occured
		) {

}
