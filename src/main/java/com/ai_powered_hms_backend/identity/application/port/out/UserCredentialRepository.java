package com.ai_powered_hms_backend.identity.application.port.out;

import java.util.List;
import java.util.Optional;

import com.ai_powered_hms_backend.identity.domain.model.UserCredential;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public interface UserCredentialRepository {

	void save(UserCredential credential);
	Optional<UserCredential> findByStaffId(StaffId staffId);
	Optional<UserCredential> findByLoginEmail(String email);
	boolean existsByLoginEmail(String email);
	
	//list of all user credentials(account roster)
	UserCredentialPage findAll(int page, int size);
	
	//nested record
	record UserCredentialPage(List<UserCredential> content, long totalElements, int page, int size) {};
}
