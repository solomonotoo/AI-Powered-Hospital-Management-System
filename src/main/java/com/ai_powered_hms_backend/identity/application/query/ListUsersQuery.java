package com.ai_powered_hms_backend.identity.application.query;

public record ListUsersQuery(int page, int size) {

	public ListUsersQuery{
		if(page < 0) throw new IllegalArgumentException("Page must not be negative");
		if(size < 1 || size > 100) throw new IllegalArgumentException("Size must be between 1 and 100");
	}
}
