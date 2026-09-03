package com.ai_powered_hms_backend.identity.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.port.in.ListUsersUseCase;
import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository;
import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository.UserCredentialPage;
import com.ai_powered_hms_backend.identity.application.query.ListUsersQuery;
import com.ai_powered_hms_backend.identity.application.query.UserSummaryResult;
import com.ai_powered_hms_backend.identity.domain.model.UserCredential;
import com.ai_powered_hms_backend.staff.application.api.StaffLookup;
import com.ai_powered_hms_backend.staff.application.api.StaffLookupSummary;

@Service
public class ListUsersService implements ListUsersUseCase{

	private final UserCredentialRepository credentialRepository;
	private final StaffLookup staffLookup;

	public ListUsersService(UserCredentialRepository credentialRepository, StaffLookup staffLookup) {
		super();
		this.credentialRepository = credentialRepository;
		this.staffLookup = staffLookup;
	}



//	@Override
//	@Transactional(readOnly = true)
//	public UsersPage list(ListUsersQuery query) {
//		UserCredentialPage page = credentialRepository.findAll(query.page(), query.size());
//		
//		// NOTE: one StaffLookup call per credential — acceptable at admin-roster
//        // scale (dozens/hundreds of users), but if this list ever needs to serve
//        // thousands of rows per page, batch this into a single bulk lookup instead.
//		List<UserSummaryResult> content = page.content().stream()
//				.map(this::toSummary)
//				.collect(Collectors.toList());
//		return new UsersPage(content,page.totalElements(),page.page(),page.size());
//	}
	
	
	@Override
	@Transactional(readOnly = true)
	public UsersPage list(ListUsersQuery query) {
		UserCredentialPage page = credentialRepository.search(query);
		
		// NOTE: one StaffLookup call per credential — acceptable at admin-roster
        // scale (dozens/hundreds of users), but if this list ever needs to serve
        // thousands of rows per page, batch this into a single bulk lookup instead.
		List<UserSummaryResult> content = page.content().stream()
				.map(this::toSummary)
				.collect(Collectors.toList());
		return new UsersPage(content,page.totalElements(),page.page(),page.size());
	}
	
	private UserSummaryResult toSummary(UserCredential credential) {
		StaffLookupSummary staff;
		try {
			staff = staffLookup.getById(credential.staffId().value());
		} catch (IllegalArgumentException e) {
			// Staff record deleted/missing but credential row still exists —
            // data-integrity edge case, surface it rather than throw and break the whole list.
            staff = new StaffLookupSummary(credential.staffId().value(), "(unknown staff)", "UNKNOWN", false);
		}
		
		return new UserSummaryResult(
				credential.staffId().value().toString(),
				staff.fullName(),
				credential.loginEmail().getValue(),
				staff.role(),
				//credential.isActive(),
				credential.status(),
				credential.mustChangePassword(),
				credential.lastLoginAt()
				);
	}

}
