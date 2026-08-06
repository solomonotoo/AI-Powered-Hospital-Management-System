package com.ai_powered_hms_backend.facility.application.service;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.FacilityCode;

public class FacilityCodeAlreadyExistsException extends RuntimeException {

	public FacilityCodeAlreadyExistsException(FacilityCode code) {
		super("A facility with code '%s' already exist".formatted(code));
		// TODO Auto-generated constructor stub
	}

}
