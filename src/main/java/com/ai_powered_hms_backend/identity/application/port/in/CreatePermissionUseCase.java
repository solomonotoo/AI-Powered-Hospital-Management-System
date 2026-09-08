package com.ai_powered_hms_backend.identity.application.port.in;

import com.ai_powered_hms_backend.identity.application.command.CreatePermissionCommand;

public interface CreatePermissionUseCase {
	void create(CreatePermissionCommand command);
}
