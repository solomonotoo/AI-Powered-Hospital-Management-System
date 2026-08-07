package com.ai_powered_hms_backend.facility.application.port.out;

import java.util.List;

import com.ai_powered_hms_backend.facility.domain.model.Facility;

public record  FacilityPage(List<Facility> content, long totalElements, int page, int size) {}
