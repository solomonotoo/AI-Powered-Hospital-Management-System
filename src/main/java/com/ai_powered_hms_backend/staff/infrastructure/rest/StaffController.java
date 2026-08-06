package com.ai_powered_hms_backend.staff.infrastructure.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.CurrentUserId;
import com.ai_powered_hms_backend.staff.application.command.OnboardStaffCommand;
import com.ai_powered_hms_backend.staff.application.port.in.OnboardStaffUseCase;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.OnboardStaffRequest;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.OnboardStaffResponse;
import com.ai_powered_hms_backend.staff.infrastructure.rest.mapper.OnboardStaffRequestMapper;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1/staff")
public class StaffController {

	private final OnboardStaffUseCase onboardStaffUseCase;

	public StaffController(OnboardStaffUseCase onboardStaffUseCase) {
		super();
		this.onboardStaffUseCase = onboardStaffUseCase;
	}
	
	@PostMapping
	public ResponseEntity<OnboardStaffResponse> onboard(
			@Valid @RequestBody OnboardStaffRequest request,
			@CurrentUserId UUID currentUserId
			){
		OnboardStaffCommand command = OnboardStaffRequestMapper.toCommand(request, currentUserId);
		StaffId staffId = onboardStaffUseCase.onboard(command);
		return ResponseEntity.status(HttpStatus.CREATED).
				body(new OnboardStaffResponse(staffId.value().toString()));
	}
}
