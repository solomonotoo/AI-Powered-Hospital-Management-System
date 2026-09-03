package com.ai_powered_hms_backend.identity.application.query;

import java.util.Set;

//public record ListUsersQuery(int page, int size) {
//
//	public ListUsersQuery{
//		if(page < 0) throw new IllegalArgumentException("Page must not be negative");
//		if(size < 1 || size > 100) throw new IllegalArgumentException("Size must be between 1 and 100");
//	}
//}


//loginEmail on UserCredentialJpaEntity is stored via EmailConverter — meaning it's a full Email domain 
//object at the JPA level, not a raw String. Email's constructor validates and rejects anything that 
//isn't a well-formed address (e.g., new Email("kof") throws InvalidEmailException). That makes it 
//impossible to do a partial-match LIKE '%kof%' search through the normal JPQL/derived-query path, 
//since Spring Data would try to construct an Email from your partial search term and fail immediately.
//
//Fix: a native SQL query for the search endpoint, bypassing the converter entirely and working against 
//the raw login_email column as plain text. Status (active) and mfaEnabled are plain booleans — no such
//problem there.

public record ListUsersQuery(
		String search, // partial match against login email, nullable
		Boolean actvie, // null = don't filter
		Boolean mfaEnabled, // null = don't filter
		String sortBy, // "loginEmail" | "lastLoginAt" | "createdAt" — validated against a whitelist
		String sortDir, //"asc" | "desc"
		int page, 
		int size) {
	
	private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("loginemail", "lastLoginAt", "createdAt");
	

	public ListUsersQuery{
		if(page < 0) throw new IllegalArgumentException("Page must not be negative");
		if(size < 1 || size > 100) throw new IllegalArgumentException("Size must be between 1 and 100");
		
		
//		Validating sortBy against a hardcoded whitelist here is deliberate — it's what makes it safe to feed into a native 
//		query's ORDER BY clause later without any SQL-injection risk, since only these three literal strings can ever reach that point.
		
		sortBy = (sortBy == null || sortBy.isBlank()) ? "loginemail" : sortBy;
		if(!ALLOWED_SORT_FIELDS.contains(sortBy)) {
			throw new IllegalArgumentException("sortBy must be one of " + ALLOWED_SORT_FIELDS);
		}
		
		sortDir = (sortDir == null || sortDir.isBlank()) ? "asc" : sortDir;
		if(!sortDir.equals("asc") && !sortDir.equals("desc")) {
			throw new IllegalArgumentException("sortDir must be 'asc' or 'desc'");
		}
	}
}
