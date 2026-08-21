package com.ai_powered_hms_backend.staff.application.port.out;

import java.util.List;

import com.ai_powered_hms_backend.staff.domain.model.StaffProfile;


public record StaffPage(List<StaffProfile> content, long totalElements, int page, int size) {

}
