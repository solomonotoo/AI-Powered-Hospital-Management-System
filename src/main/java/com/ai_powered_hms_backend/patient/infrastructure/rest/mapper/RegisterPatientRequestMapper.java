package com.ai_powered_hms_backend.patient.infrastructure.rest.mapper;

import java.util.UUID;

import com.ai_powered_hms_backend.patient.application.command.RegisterPatientCommand;
import com.ai_powered_hms_backend.patient.domain.enums.Genotype;
import com.ai_powered_hms_backend.patient.domain.enums.IdType;
import com.ai_powered_hms_backend.patient.domain.enums.PreferredLanguage;
import com.ai_powered_hms_backend.patient.domain.enums.Relationship;
import com.ai_powered_hms_backend.patient.domain.enums.Religion;
import com.ai_powered_hms_backend.patient.domain.valueobjects.ContactDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.DateOfBirth;
import com.ai_powered_hms_backend.patient.domain.valueobjects.InsuranceInformation;
import com.ai_powered_hms_backend.patient.domain.valueobjects.MedicalDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.NationalId;
import com.ai_powered_hms_backend.patient.domain.valueobjects.NextOfKin;
import com.ai_powered_hms_backend.patient.domain.valueobjects.PersonalDetails;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.AddressRequest;
import com.ai_powered_hms_backend.patient.infrastructure.rest.dto.RegisterPatientRequest;
import com.ai_powered_hms_backend.shared_kernel.enums.BloodGroup;
import com.ai_powered_hms_backend.shared_kernel.enums.Gender;
import com.ai_powered_hms_backend.shared_kernel.enums.MaritalStatus;
import com.ai_powered_hms_backend.shared_kernel.enums.PatientType;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

// final RegisterPatientRequestMapper -
public class RegisterPatientRequestMapper {

	public static RegisterPatientCommand toCommand(RegisterPatientRequest request, UUID registerdBy) {
		
		PersonalDetails personalDetails = new PersonalDetails(
				new PersonName(
						request.personalDetails().firstName(),
						request.personalDetails().lastName(),
						request.personalDetails().maidenName(),
						request.personalDetails().preferredName()),
				Gender.valueOf(request.personalDetails().gender().toUpperCase()),
				MaritalStatus.valueOf(request.personalDetails().maritalStatus().toUpperCase()), 
				DateOfBirth.of(request.personalDetails().dateOfBirth()),
				Religion.valueOf(request.personalDetails().religion().toUpperCase()),
				request.personalDetails().nationality(),
				request.personalDetails().ethnicity(), 
				request.personalDetails().occupation()
				);
		
		MedicalDetails medicalDetails = new MedicalDetails(
				BloodGroup.valueOf(request.medicalDetails().bloodGroup().toUpperCase()),
				Genotype.valueOf(request.medicalDetails().genoType().toUpperCase()),
				request.medicalDetails().nationalIdNumber() == null 
				                      ? null
				                      : new NationalId(
				                      IdType.valueOf(request.medicalDetails().nationalIdType().toUpperCase()),
				                      request.medicalDetails().nationalIdNumber(),
				                      request.medicalDetails().nationalIdIssuingCountry()
				                      )
				
				);
		
		ContactDetails contactDetails = new ContactDetails(
				toAddress(request.contactDetails().homeAddress()),
				new PhoneNumber(request.contactDetails().phoneNumber()),
				request.contactDetails().alternatePhone() == null
						? null
						: new PhoneNumber(request.contactDetails().alternatePhone()),
				request.contactDetails().email() == null
						? null
						: new Email(request.contactDetails().email())
				
				
				);
		
		NextOfKin nextOfKin = new NextOfKin(request.nextOfKin().fullName(),
				Relationship.valueOf(request.nextOfKin().relationship().toUpperCase()),
				new PhoneNumber(request.nextOfKin().phoneNumber()), 
				request.nextOfKin().address() == null ? null : toAddress(request.nextOfKin().address())
			);
		
		InsuranceInformation insuranceInformation = request.insuranceInformation() == null
				? null
				: new InsuranceInformation(request.insuranceInformation().provider(), 
						request.insuranceInformation().policyNumber(), 
						request.insuranceInformation().groupNumber(), 
						request.insuranceInformation().coverageStartDate(), 
						request.insuranceInformation().expirationDate()
				);
		
		return new RegisterPatientCommand(
				personalDetails,
				medicalDetails,
				contactDetails,
				nextOfKin,
				insuranceInformation,
				PatientType.valueOf(request.patientType().toUpperCase()),
				PreferredLanguage.valueOf(request.preferredLanguage().toUpperCase()),
				request.facilityId(),
				registerdBy
				);
		
	}
	private static Address toAddress(AddressRequest a) {
        return new Address(a.line1(), a.line2(), a.city(), a.state(), a.postalCode(), a.country());
    }
}
