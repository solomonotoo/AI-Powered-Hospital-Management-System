package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ai_powered_hms_backend.patient.application.port.out.PatientRepository;
import com.ai_powered_hms_backend.patient.domain.model.Patient;
import com.ai_powered_hms_backend.shared_kernel.ids.PatientId;

@Repository
public class PatientRepositoryAdaptor implements PatientRepository{
	
	private final PatientJpaRepository jpaRepository;
	

	public PatientRepositoryAdaptor(PatientJpaRepository jpaRepository) {
		super();
		this.jpaRepository = jpaRepository;
	}

	@Override
	public void save(Patient patient) {
		PatientJpaEntity jpaEntity = PatientPersistenceMapper.toEntity(patient);
		jpaRepository.save(jpaEntity);
	}

	@Override
	public Optional<Patient> findBy(PatientId id) {
		// TODO Auto-generated method stub
		return jpaRepository.findById(id.value())
				.map(PatientPersistenceMapper :: toDomain);
	}

}
