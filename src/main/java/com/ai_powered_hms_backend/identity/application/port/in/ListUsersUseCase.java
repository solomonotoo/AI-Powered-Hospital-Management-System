package com.ai_powered_hms_backend.identity.application.port.in;

import java.util.List;

import com.ai_powered_hms_backend.identity.application.query.ListUsersQuery;
import com.ai_powered_hms_backend.identity.application.query.UserSummaryResult;

public interface ListUsersUseCase {
	UsersPage list(ListUsersQuery query);
	
	//nested record
	record UsersPage(List<UserSummaryResult> content, long totalElements, int page, int size) {}
}
