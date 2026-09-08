package com.ai_powered_hms_backend.identity.application.command;

import java.util.Set;
import java.util.UUID;

public record CreateRoleCommand(String name,String description,Set<String> permissionCode,UUID createdBy) {

}
