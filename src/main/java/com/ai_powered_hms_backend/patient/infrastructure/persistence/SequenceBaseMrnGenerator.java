package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.patient.application.port.out.MedicalRecordNumberGenerator;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.MRN;

import lombok.RequiredArgsConstructor;

@Component
//@RequiredArgsConstructor
public class SequenceBaseMrnGenerator implements MedicalRecordNumberGenerator{

	 private final JdbcTemplate jdbcTemplate;

	    public SequenceBaseMrnGenerator(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

		@Override
	    public MRN generateFor(String facilityCode) {
	        // Atomic per-facility increment, race-safe
	        Long next = jdbcTemplate.queryForObject(
	            """
	            INSERT INTO facility_mrn_sequence (facility_code, last_value)
	            VALUES (?, 1)
	            ON CONFLICT (facility_code)
	            DO UPDATE SET last_value = facility_mrn_sequence.last_value + 1
	            RETURNING last_value
	            """,
	            Long.class,
	            facilityCode
	        );

	        String padded = String.format("%06d", next); // 000123
	        return new MRN(facilityCode, padded);
	    }
}
