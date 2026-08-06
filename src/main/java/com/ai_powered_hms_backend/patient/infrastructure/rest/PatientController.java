package com.ai_powered_hms_backend.patient.infrastructure.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_powered_hms_backend.patient.application.command.RegisterPatientCommand;
import com.ai_powered_hms_backend.patient.application.port.in.RegisterPatientUseCase;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.RegisterPatientRequest;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.RegisterPatientResponse;
import com.ai_powered_hms_backend.patient.infrastructure.rest.mapper.RegisterPatientRequestMapper;
import com.ai_powered_hms_backend.shared_kernel.ids.PatientId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.CurrentUserId;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

	private final RegisterPatientUseCase registerPatientUseCase;

    public PatientController(RegisterPatientUseCase registerPatientUseCase) {
        this.registerPatientUseCase = registerPatientUseCase;
    }

//    @PostMapping
//    public ResponseEntity<RegisterPatientResponse> register(
//            @Valid @RequestBody RegisterPatientRequest request,
//            /* @AuthenticationPrincipal */ UUID currentUserId // placeholder until identity module exists
//    ) {
//        RegisterPatientCommand command = RegisterPatientRequestMapper.toCommand(request, currentUserId);
//
//        PatientId patientId = registerPatientUseCase.register(command);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(new RegisterPatientResponse(patientId.value().toString()));
//    }
    
    @PostMapping
   //@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<RegisterPatientResponse> register(
            @Valid @RequestBody RegisterPatientRequest request,
            @CurrentUserId UUID currentUserId 
    ) {
        RegisterPatientCommand command = RegisterPatientRequestMapper.toCommand(request, currentUserId);

        PatientId patientId = registerPatientUseCase.register(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterPatientResponse(patientId.value().toString()));
    }
}
