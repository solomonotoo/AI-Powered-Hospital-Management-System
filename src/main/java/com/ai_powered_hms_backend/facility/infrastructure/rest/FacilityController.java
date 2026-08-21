package com.ai_powered_hms_backend.facility.infrastructure.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ai_powered_hms_backend.facility.application.command.OnboardFacilityCommand;
import com.ai_powered_hms_backend.facility.application.command.UpdateFacilityCommand;
import com.ai_powered_hms_backend.facility.application.port.in.*;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityPage;
import com.ai_powered_hms_backend.facility.application.query.FacilitySummaryResult;
import com.ai_powered_hms_backend.facility.application.query.ListFacilitiesQuery;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.*;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.rest.ApiResponse;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.rest.PagedResponse;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.CurrentUserId;

@RestController
@RequestMapping("/api/v1/facilities")
public class FacilityController {

    private final OnboardFacilityUseCase onboardFacilityUseCase;
    private final GetFacilitiesUseCase getFacilityUseCase;
    private final ListFacilitiesUseCase listFacilitiesUseCase;
    private final UpdateFacilityUseCase updateFacilityUseCase;
    private final ChangeFacilityStatusUseCase changeFacilityStatusUseCase;
    private final GetFacilitySummaryUseCase getFacilitySummaryUseCase;

   

   

	public FacilityController(
			OnboardFacilityUseCase onboardFacilityUseCase, GetFacilitiesUseCase getFacilityUseCase,
			ListFacilitiesUseCase listFacilitiesUseCase, UpdateFacilityUseCase updateFacilityUseCase,
			ChangeFacilityStatusUseCase changeFacilityStatusUseCase,
			GetFacilitySummaryUseCase getFacilitySummaryUseCase) {
		super();
		this.onboardFacilityUseCase = onboardFacilityUseCase;
		this.getFacilityUseCase = getFacilityUseCase;
		this.listFacilitiesUseCase = listFacilitiesUseCase;
		this.updateFacilityUseCase = updateFacilityUseCase;
		this.changeFacilityStatusUseCase = changeFacilityStatusUseCase;
		this.getFacilitySummaryUseCase = getFacilitySummaryUseCase;
	}

	@PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<FacilityResponse> onboard(
            @Valid @RequestBody OnboardFacilityRequest request,
            @CurrentUserId UUID currentUserId
    ) {
        OnboardFacilityCommand command = OnboardFacilityRequestMapper.toCommand(request, currentUserId);
        FacilityId facilityId = onboardFacilityUseCase.onboard(command);
        Facility facility = getFacilityUseCase.getById(facilityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(FacilityResponseMapper.toResponse(facility));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacilityResponse> getById(@PathVariable UUID id) {
        Facility facility = getFacilityUseCase.getById(FacilityId.of(id));
        return ResponseEntity.ok(FacilityResponseMapper.toResponse(facility));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<FacilityResponse>>> list(
            @RequestParam(required = false) FacilityStatus status,
            @RequestParam(required = false) FacilityType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        FacilityPage result = listFacilitiesUseCase.list(new ListFacilitiesQuery(status, type, page, size));

        List<FacilityResponse> facilities = result.content().stream()
                .map(FacilityResponseMapper::toResponse)
                .collect(Collectors.toList());

        PagedResponse<FacilityResponse> pagedResponse =
                PagedResponse.of(
                        facilities,
                        result.page(),
                        result.size(),
                        result.totalElements()
                );

        ApiResponse<PagedResponse<FacilityResponse>> response =
                ApiResponse.success(
                        "Facilities retrieved successfully",
                        pagedResponse
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<FacilitySummaryResponse>> getSummary() {

     
               FacilitySummaryResult result = getFacilitySummaryUseCase.getSummary();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Facility summary retrieved successfully",
                        FacilitySummaryResponseMapper.toResponse(result)
                )
        );
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<FacilityResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFacilityRequest request,
            @CurrentUserId UUID currentUserId
    ) {
        UpdateFacilityCommand command = UpdateFacilityRequestMapper.toCommand(id, request, currentUserId);
        updateFacilityUseCase.update(command);
        Facility facility = getFacilityUseCase.getById(FacilityId.of(id));
        return ResponseEntity.ok(FacilityResponseMapper.toResponse(facility));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id, @CurrentUserId UUID currentUserId) {
        changeFacilityStatusUseCase.deactivate(FacilityId.of(id), currentUserId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id, @CurrentUserId UUID currentUserId) {
        changeFacilityStatusUseCase.reactivate(FacilityId.of(id), currentUserId);
        return ResponseEntity.noContent().build();
    }
}