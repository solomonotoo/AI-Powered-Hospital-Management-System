package com.ai_powered_hms_backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ai_powered_hms_backend.patient.application.command.RegisterPatientCommand;
import com.ai_powered_hms_backend.patient.application.port.out.FacilityLookupPort;
import com.ai_powered_hms_backend.patient.application.port.out.FacilityLookupPort.FacilitySummary;
import com.ai_powered_hms_backend.patient.application.port.out.MedicalRecordNumberGenerator;
import com.ai_powered_hms_backend.patient.application.port.out.PatientRepository;
import com.ai_powered_hms_backend.patient.application.service.RegisterPatientService;
import com.ai_powered_hms_backend.patient.domain.enums.Genotype;
import com.ai_powered_hms_backend.patient.domain.enums.PreferredLanguage;
import com.ai_powered_hms_backend.patient.domain.enums.Relationship;
import com.ai_powered_hms_backend.patient.domain.enums.Religion;
import com.ai_powered_hms_backend.patient.domain.valueobjects.ContactDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.DateOfBirth;
import com.ai_powered_hms_backend.patient.domain.valueobjects.MedicalDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.NextOfKin;
import com.ai_powered_hms_backend.patient.domain.valueobjects.PersonalDetails;
import com.ai_powered_hms_backend.shared_kernel.enums.BloodGroup;
import com.ai_powered_hms_backend.shared_kernel.enums.Gender;
import com.ai_powered_hms_backend.shared_kernel.enums.MaritalStatus;
import com.ai_powered_hms_backend.shared_kernel.enums.PatientType;
import com.ai_powered_hms_backend.shared_kernel.ids.PatientId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.MRN;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

@ExtendWith(MockitoExtension.class)
public class RegisterPatientServiceTest {
	@Mock FacilityLookupPort facilityLookupPort;
    @Mock MedicalRecordNumberGenerator mrnGenerator;
    @Mock PatientRepository patientRepository;

    @InjectMocks RegisterPatientService service;

    @Test
    void registersPatientAndSavesIt() {
        UUID facilityId = UUID.randomUUID();
        UUID registeredBy = UUID.randomUUID();

        when(facilityLookupPort.getById(facilityId))
                .thenReturn(new FacilitySummary(facilityId, "KBTH", "Korle-Bu Teaching Hospital"));
        when(mrnGenerator.generateFor("KBTH"))
                .thenReturn(new MRN("KBTH", "000123"));

        RegisterPatientCommand command = new RegisterPatientCommand(
                new PersonalDetails(new PersonName("Ama", "Mensah"), Gender.FEMALE, MaritalStatus.SINGLE,
                        DateOfBirth.of(LocalDate.of(1995, 4, 12)), Religion.CHRISTIANITY,
                        "Ghanaian", "Akan", "Teacher"),
                new MedicalDetails(BloodGroup.O_POSITIVE, Genotype.AA, null),
                new ContactDetails(
                        new Address("12 Ring Rd", null, "Accra", "Greater Accra", "00233", "Ghana"),
                        new PhoneNumber("+233241234567"), null, null),
                new NextOfKin("Kwame Mensah", Relationship.SPOUSE, new PhoneNumber("+233247654321"), null),
                null,
                PatientType.OUTPATIENT,
                PreferredLanguage.ENGLISH,
                facilityId,
                registeredBy
        );

        PatientId result = service.register(command);

        assertThat(result).isNotNull();
        verify(mrnGenerator).generateFor("KBTH");
        verify(patientRepository).save(any());
    }
}
