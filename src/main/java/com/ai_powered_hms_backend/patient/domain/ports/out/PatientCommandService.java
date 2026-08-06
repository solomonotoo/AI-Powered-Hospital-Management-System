package com.ai_powered_hms_backend.patient.domain.ports.out;


//WHY: This port is used by the WRITE side (UserCommandService) only. It saves and loads the full domain aggregate. 
//The adapter maps between the domain User and the JPA UserJpaEntity.
public class PatientCommandService {

}
