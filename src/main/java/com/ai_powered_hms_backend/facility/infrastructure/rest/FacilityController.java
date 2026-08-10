package com.ai_powered_hms_backend.facility.infrastructure.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai_powered_hms_backend.facility.application.command.OnboardFacilityCommand;
import com.ai_powered_hms_backend.facility.application.command.UpdateFacilityCommand;
import com.ai_powered_hms_backend.facility.application.port.in.ChangeFacilityStatusUseCase;
import com.ai_powered_hms_backend.facility.application.port.in.GetFacilitiesUseCase;
import com.ai_powered_hms_backend.facility.application.port.in.ListFacilitiesUseCase;
import com.ai_powered_hms_backend.facility.application.port.in.OnboardFacilityUseCase;
import com.ai_powered_hms_backend.facility.application.port.in.UpdateFacilityUseCase;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityPage;
import com.ai_powered_hms_backend.facility.application.query.ListFacilitiesQuery;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.FacilityListResponse;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.FacilityResponse;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.FacilityResponseMapper;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.OnboardFacilityRequest;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.OnboardFacilityRequestMapper;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.UpdateFacilityRequest;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.UpdateFacilityRequestMapper;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.CurrentUserId;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/facilities")
public class FacilityController {

	private final OnboardFacilityUseCase onboardFacilityUseCase;
	private final GetFacilitiesUseCase getFacilitiesUseCase;
	private final ListFacilitiesUseCase listFacilitiesUseCase;
	private final UpdateFacilityUseCase updateFacilityUseCase;
	private final ChangeFacilityStatusUseCase changeFacilityStatusUseCase;
	

	
	public FacilityController(OnboardFacilityUseCase onboardFacilityUseCase, GetFacilitiesUseCase getFacilitiesUseCase,
			ListFacilitiesUseCase listFacilitiesUseCase, UpdateFacilityUseCase updateFacilityUseCase,
			ChangeFacilityStatusUseCase changeFacilityStatusUseCase) {
		super();
		this.onboardFacilityUseCase = onboardFacilityUseCase;
		this.getFacilitiesUseCase = getFacilitiesUseCase;
		this.listFacilitiesUseCase = listFacilitiesUseCase;
		this.updateFacilityUseCase = updateFacilityUseCase;
		this.changeFacilityStatusUseCase = changeFacilityStatusUseCase;
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
	public ResponseEntity<FacilityResponse> onboard(
			@Valid @RequestBody OnboardFacilityRequest request,
			@CurrentUserId UUID currentUserId 
			){
		
		OnboardFacilityCommand command = OnboardFacilityRequestMapper.toCommand(request, currentUserId);
	
		FacilityId facilityId = onboardFacilityUseCase.onboard(command);
		Facility facility = getFacilitiesUseCase.getById(facilityId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(FacilityResponseMapper.toResponse(facility));
	
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<FacilityResponse> getById(@PathVariable UUID id){
		Facility facility = getFacilitiesUseCase.getById(FacilityId.of(id));
		return ResponseEntity.ok(FacilityResponseMapper.toResponse(facility));
	}
	
	
	@GetMapping
	public ResponseEntity<FacilityListResponse> list(
			@RequestParam(required = false) FacilityStatus status,
			@RequestParam(required = false) FacilityType type,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size 
			
			){
		FacilityPage result = listFacilitiesUseCase.list(new ListFacilitiesQuery(status, type, page, size));
		
		List<FacilityResponse> facilities = result.content().stream()
				.map(FacilityResponseMapper :: toResponse)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new FacilityListResponse(
				facilities,result.totalElements(),result.page(),result.size()
				));
		
		
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public ResponseEntity<FacilityResponse> update(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateFacilityRequest request,
			@CurrentUserId UUID currentUserId
			){
		
		UpdateFacilityCommand command = UpdateFacilityRequestMapper.toCommand(id, request, currentUserId);
		updateFacilityUseCase.update(command);
		Facility facility = getFacilitiesUseCase.getById(FacilityId.of(id));
		
		return ResponseEntity.ok(FacilityResponseMapper.toResponse(facility));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	public ResponseEntity<Void> deactivate(@PathVariable UUID id, @CurrentUserId UUID currentUserId){
		changeFacilityStatusUseCase.deactivate(FacilityId.of(id), currentUserId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/{id}/reactivate")
	@PreAuthorize("hasAnyrole('ADMIN','SUPER_ADMIN')")
	public ResponseEntity<Void> reactivate(@PathVariable UUID id, @CurrentUserId UUID currentUserId){
		changeFacilityStatusUseCase.reactivate(FacilityId.of(id), currentUserId);
		return ResponseEntity.noContent().build();
	}
}
