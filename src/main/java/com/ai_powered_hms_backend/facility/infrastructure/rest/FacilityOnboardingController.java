package com.ai_powered_hms_backend.facility.infrastructure.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai_powered_hms_backend.facility.application.command.OnboardFacilityCommand;
import com.ai_powered_hms_backend.facility.application.port.in.OnboardFacilityUseCase;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.OnboardFacilityRequest;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.OnboardFacilityRequestMapper;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.CurrentUserId;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1/facilites")
public class FacilityOnboardingController {

	private final OnboardFacilityUseCase onboardFacilityUseCase;
	
	public FacilityOnboardingController(OnboardFacilityUseCase onboardFacilityUseCase) {
		this.onboardFacilityUseCase = onboardFacilityUseCase;
	}
	
//	@PostMapping
//	public ResponseEntity<OnboardFacilityResponse> onboard(
//			@Valid @RequestBody OnboardFacilityRequest request,
//			/* @AuthenticationPrincipal */ UUID currentUserId // placeholder until identity module exists
//			){
//		OnboardFacilityCommand command = OnboardFacilityRequestMapper.toCommand(request, currentUserId);
//	
//		FacilityId facilityId = onboardFacilityUseCase.onboard(command);
//		
//		return ResponseEntity.status(HttpStatus.CREATED)
//				.body(new OnboardFacilityResponse(facilityId.value().toString()));
//	
//	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public ResponseEntity<OnboardFacilityResponse> onboard(
			@Valid @RequestBody OnboardFacilityRequest request,
			@CurrentUserId UUID currentUserId 
			){
		OnboardFacilityCommand command = OnboardFacilityRequestMapper.toCommand(request, currentUserId);
	
		FacilityId facilityId = onboardFacilityUseCase.onboard(command);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new OnboardFacilityResponse(facilityId.value().toString()));
	
	}
	
	
}

record OnboardFacilityResponse(String facilityId) {}
