package com.ai_powered_hms_backend.identity.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.port.in.CreateUserCredentialCommand;
import com.ai_powered_hms_backend.identity.application.port.out.PasswordHasher;
import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository;
import com.ai_powered_hms_backend.identity.domain.model.UserCredential;
import com.ai_powered_hms_backend.identity.port.in.CreateUserCredentialUseCase;
import com.ai_powered_hms_backend.staff.application.api.StaffLookup;
import com.ai_powered_hms_backend.staff.application.api.StaffSummary;

@Service
public class CreateUserCredentialService implements CreateUserCredentialUseCase{

	private final StaffLookup staffLookup;// identity -> staff, one direction only
	private final UserCredentialRepository credentialRepository;
	private final PasswordHasher passwordHasher;
		
	public CreateUserCredentialService(StaffLookup staffLookup, UserCredentialRepository credentialRepository,
			PasswordHasher passwordHasher) {
		super();
		this.staffLookup = staffLookup;
		this.credentialRepository = credentialRepository;
		this.passwordHasher = passwordHasher;
	}


	@Override
	@Transactional
	public void create(CreateUserCredentialCommand command) {
		StaffSummary staff = staffLookup.getById(command.staffId().value());
		if(!staff.active()) {
			throw new IllegalStateException("Cannot create credentials for an inactive staff member");
		}
		if(credentialRepository.existsByLoginEmail(command.loginEmail().getValue())) {
			throw new IllegalStateException("Login email already in use: " + command.loginEmail());
		}
		
		UserCredential credential = UserCredential.create(
				command.staffId(), command.loginEmail(),
				passwordHasher.hash(command.rawPassword()), 
				// creator = self at bootstrap-provision time; adjust if an admin 
				//should be recorded instead	
				command.staffId().value()
				);
		credentialRepository.save(credential);
	}

}
