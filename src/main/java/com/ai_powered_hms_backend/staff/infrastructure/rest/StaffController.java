package com.ai_powered_hms_backend.staff.infrastructure.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.rest.ApiResponse;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.rest.PagedResponse;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.CurrentUserId;
import com.ai_powered_hms_backend.staff.application.command.OnboardStaffCommand;
import com.ai_powered_hms_backend.staff.application.port.in.GetStaffSummaryUseCase;
import com.ai_powered_hms_backend.staff.application.port.in.ListStaffUseCase;
import com.ai_powered_hms_backend.staff.application.port.in.OnboardStaffUseCase;
import com.ai_powered_hms_backend.staff.application.port.out.StaffPage;
import com.ai_powered_hms_backend.staff.application.query.ListStaffQuery;
import com.ai_powered_hms_backend.staff.application.query.StaffSummaryResult;
import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.OnboardStaffRequest;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.OnboardStaffResponse;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.StaffProfileResponse;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.StaffProfileResponseMapper;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.StaffSummaryResponse;
import com.ai_powered_hms_backend.staff.infrastructure.rest.dto.StaffSummaryResponseMapper;
import com.ai_powered_hms_backend.staff.infrastructure.rest.mapper.OnboardStaffRequestMapper;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1/staff")
public class StaffController {

	private final OnboardStaffUseCase onboardStaffUseCase;
	private final ListStaffUseCase listStaffUseCase;
	private final GetStaffSummaryUseCase getStaffSummaryUseCase;

	public StaffController(OnboardStaffUseCase onboardStaffUseCase, ListStaffUseCase listStaffUseCase,
			GetStaffSummaryUseCase getStaffSummaryUseCase) {
		super();
		this.onboardStaffUseCase = onboardStaffUseCase;
		this.listStaffUseCase = listStaffUseCase;
		this.getStaffSummaryUseCase = getStaffSummaryUseCase;
	}


	@PostMapping
	@PreAuthorize("hasAuthority('STAFF_MANAGE')")
	public ResponseEntity<OnboardStaffResponse> onboard(
			@Valid @RequestBody OnboardStaffRequest request,
			@CurrentUserId UUID currentUserId
			){
		OnboardStaffCommand command = OnboardStaffRequestMapper.toCommand(request, currentUserId);
		StaffId staffId = onboardStaffUseCase.onboard(command);
		return ResponseEntity.status(HttpStatus.CREATED).
				body(new OnboardStaffResponse(staffId.value().toString()));
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<PagedResponse<StaffProfileResponse>>> list(
			 @RequestParam(required = false) StaffRole role,
			 @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "20") int size
			){
		
		StaffPage result = listStaffUseCase.list(new ListStaffQuery(role, page, size));
		
		List<StaffProfileResponse> staff = result.content().stream()
				.map(StaffProfileResponseMapper :: toResponse)
				.collect(Collectors.toList());
		
		PagedResponse<StaffProfileResponse> pagedResponse = PagedResponse.of(staff, result.page(),
				result.size(), result.totalElements());
		
		ApiResponse<PagedResponse<StaffProfileResponse>> response = 
				ApiResponse.success(  "Staff data retrieved successfully",
                        pagedResponse);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<StaffSummaryResponse>> getSummary(){
		StaffSummaryResult result = getStaffSummaryUseCase.getSummary();
		
		return ResponseEntity.ok(
                ApiResponse.success(
                        "Facility summary retrieved successfully",
                        StaffSummaryResponseMapper.toResponse(result)
                )
        );
	}
	
	
}
