

CREATE TABLE patients (
    id UUID PRIMARY KEY,

    -- MRN
    mrn_facility_code VARCHAR(10) NOT NULL,
    mrn_value VARCHAR(20) NOT NULL,

    -- Personal details (PersonNameEmbeddable + flattened fields)
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    maiden_name VARCHAR(45),
    preferred_name VARCHAR(45),

    gender VARCHAR(20) NOT NULL,
    marital_status VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    religion VARCHAR(30) NOT NULL,
    nationality VARCHAR(100) NOT NULL,
    ethnicity VARCHAR(100) NOT NULL,
    occupation VARCHAR(100) NOT NULL,

    -- Medical details
    blood_group VARCHAR(10) NOT NULL,
    genotype VARCHAR(10) NOT NULL,

    -- NationalIdEmbeddable (optional)
    national_id_type VARCHAR(30),
    national_id_number VARCHAR(40),
    national_id_issuing_country VARCHAR(56),

    -- Contact details
    home_address_line1 VARCHAR(200) NOT NULL,
    home_address_line2 VARCHAR(200),
    home_city VARCHAR(100) NOT NULL,
    home_state VARCHAR(100),
    home_postal_code VARCHAR(20),
    home_country VARCHAR(100) NOT NULL,

    phone_number VARCHAR(20) NOT NULL,
    alternate_number VARCHAR(20),
    email VARCHAR(150),

    -- NextOfKinEmbeddable
    kin_full_name VARCHAR(100) NOT NULL,
    kin_relationship VARCHAR(30) NOT NULL,
    kin_phone_number VARCHAR(20) NOT NULL,
    kin_address_line1 VARCHAR(200),
    kin_address_line2 VARCHAR(200),
    kin_city VARCHAR(100),
    kin_state VARCHAR(100),
    kin_postal_code VARCHAR(20),
    kin_country VARCHAR(100),

    -- InsuranceInformationEmbeddable (optional)
    insurance_provider VARCHAR(150),
    insurance_policy_number VARCHAR(100),
    insurance_group_number VARCHAR(100),
    insurance_coverage_start_date DATE,
    insurance_expiration_date DATE,

    -- Patient state
    patient_type VARCHAR(20) NOT NULL,
    patient_status VARCHAR(20) NOT NULL,
    registration_date DATE NOT NULL,
    preferred_language VARCHAR(30) NOT NULL,

    -- ConsentInformationEmbeddable
    consent_to_treat BOOLEAN NOT NULL,
    consent_to_share_data BOOLEAN NOT NULL,
    treatment_consent_given_at TIMESTAMP,
    data_consent_given_at TIMESTAMP,
    consent_updated_at TIMESTAMP,
    consent_updated_by UUID,

    -- AuditMetadataEmbeddable
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    created_by UUID NOT NULL,
    updated_by UUID,

    CONSTRAINT uq_patients_mrn UNIQUE (mrn_facility_code, mrn_value)
);