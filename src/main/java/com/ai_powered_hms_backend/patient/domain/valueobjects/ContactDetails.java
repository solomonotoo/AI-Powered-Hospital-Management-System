package com.ai_powered_hms_backend.patient.domain.valueobjects;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

public record ContactDetails(
		Address homeAddress,
		PhoneNumber phoneNumber,
		PhoneNumber alternatePhoneNumber,
		Email email
		) {

}
