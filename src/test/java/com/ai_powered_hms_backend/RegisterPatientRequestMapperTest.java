package com.ai_powered_hms_backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ai_powered_hms_backend.patient.application.command.RegisterPatientCommand;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.AddressRequest;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.ContactDetailsRequest;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.MedicalDetailsRequest;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.NextOfKinRequest;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.PersonalDetailsRequest;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.RegisterPatientRequest;
import com.ai_powered_hms_backend.patient.infrastructure.rest.mapper.RegisterPatientRequestMapper;

public class RegisterPatientRequestMapperTest {

	@Test
	void mappCompleteRequestInCommand() {
		RegisterPatientRequest request = new RegisterPatientRequest(
                new PersonalDetailsRequest("Ama", "Mensah", null, null,
                        "FEMALE", "SINGLE", LocalDate.of(1995, 4, 12),
                        "CHRISTIANITY", "Ghanaian", "Akan", "Teacher"),
                new MedicalDetailsRequest("O_POSITIVE", "AA", null, null, null),
                new ContactDetailsRequest(
                        new AddressRequest("12 Ring Rd", null, "Accra", "Greater Accra", "00233", "Ghana"),
                        "+233241234567", null, null
                ),
                new NextOfKinRequest("Kwame Mensah", "SPOUSE", "+233247654321", null),
                null,
                "OUTPATIENT",
                "ENGLISH",
                UUID.randomUUID()
        );
		
		 RegisterPatientCommand command = RegisterPatientRequestMapper.toCommand(request, UUID.randomUUID());

	        assertThat(command.personalDetails().fullName().firstName()).isEqualTo("Ama");
	        assertThat(command.contactDetails().phoneNumber().value()).isEqualTo("+233241234567");
	        assertThat(command.nextOfKin().relationship().name()).isEqualTo("SPOUSE");
	        assertThat(command.insuranceInformation()).isNull();
	}
}
