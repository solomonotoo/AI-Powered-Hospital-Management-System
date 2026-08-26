package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssignRoleRequest(
		UUID roleId,LocalDateTime expiresAt
		) {

}
