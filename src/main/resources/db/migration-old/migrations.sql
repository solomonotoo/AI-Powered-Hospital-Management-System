-- ═══════════════════════════════════════════════════════════════════════════
-- V1__create_shared_schema.sql
-- Shared infrastructure: enums, audit columns, event publication table
-- ═══════════════════════════════════════════════════════════════════════════

-- Spring Modulith event publication outbox table
CREATE TABLE IF NOT EXISTS event_publication (
    id               UUID        NOT NULL PRIMARY KEY,
    listener_id      TEXT        NOT NULL,
    event_type       TEXT        NOT NULL,
    serialized_event TEXT        NOT NULL,
    publication_date TIMESTAMPTZ NOT NULL,
    completion_date  TIMESTAMPTZ
);

CREATE INDEX idx_event_pub_completion ON event_publication (completion_date)
    WHERE completion_date IS NULL;

-- ═══════════════════════════════════════════════════════════════════════════
-- V2__create_patient_tables.sql
-- Patient Management bounded context
-- ═══════════════════════════════════════════════════════════════════════════

CREATE TABLE IF NOT EXISTS patient (
    patient_id              UUID        NOT NULL PRIMARY KEY,
    mrn                     VARCHAR(20) NOT NULL UNIQUE,
    first_name              VARCHAR(100) NOT NULL,
    last_name               VARCHAR(100) NOT NULL,
    preferred_name          VARCHAR(100),
    date_of_birth           DATE        NOT NULL,
    gender                  VARCHAR(20) NOT NULL
                                CHECK (gender IN ('MALE','FEMALE','OTHER','PREFER_NOT_TO_SAY')),
    national_id             VARCHAR(50),
    blood_group             VARCHAR(5)
                                CHECK (blood_group IN ('A+','A-','B+','B-','AB+','AB-','O+','O-')),
    marital_status          VARCHAR(20)
                                CHECK (marital_status IN ('SINGLE','MARRIED','DIVORCED','WIDOWED','OTHER')),
    religion                VARCHAR(50),
    ethnicity               VARCHAR(50),
    occupation              VARCHAR(100),
    nationality             VARCHAR(50),
    email                   VARCHAR(150),
    phone                   VARCHAR(20) NOT NULL,
    alternate_phone         VARCHAR(20),
    address_line1           VARCHAR(200),
    address_line2           VARCHAR(200),
    city                    VARCHAR(100),
    state_region            VARCHAR(100),
    country                 VARCHAR(100),
    postal_code             VARCHAR(20),
    next_of_kin_name        VARCHAR(150),
    next_of_kin_relationship VARCHAR(50),
    next_of_kin_phone       VARCHAR(20),
    next_of_kin_email       VARCHAR(150),
    insurance_provider      VARCHAR(150),
    insurance_policy_number VARCHAR(80),
    insurance_group_number  VARCHAR(80),
    insurance_expiry_date   DATE,
    patient_type            VARCHAR(20) NOT NULL DEFAULT 'OPD'
                                CHECK (patient_type IN ('OPD','IPD','EMERGENCY','DAYCASE')),
    registration_date       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    registered_by           UUID,
    is_active               BOOLEAN     NOT NULL DEFAULT TRUE,
    consent_to_treat        BOOLEAN     NOT NULL DEFAULT FALSE,
    consent_to_share_data   BOOLEAN     NOT NULL DEFAULT FALSE,
    preferred_language      VARCHAR(30),
    created_at              TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_patient_mrn        ON patient (mrn);
CREATE INDEX idx_patient_phone      ON patient (phone);
CREATE INDEX idx_patient_national_id ON patient (national_id);
CREATE INDEX idx_patient_name       ON patient (last_name, first_name);

-- MRN sequence for auto-generation (adjust prefix per hospital)
CREATE SEQUENCE IF NOT EXISTS mrn_seq START 100001 INCREMENT 1;

-- ═══════════════════════════════════════════════════════════════════════════
-- V3__create_staff_tables.sql
-- Staff / System User bounded context
-- ═══════════════════════════════════════════════════════════════════════════

CREATE TABLE IF NOT EXISTS staff (
    staff_id          UUID         NOT NULL PRIMARY KEY,
    employee_number   VARCHAR(30)  NOT NULL UNIQUE,
    first_name        VARCHAR(100) NOT NULL,
    last_name         VARCHAR(100) NOT NULL,
    role              VARCHAR(30)  NOT NULL
                          CHECK (role IN ('DOCTOR','NURSE','ADMIN','BILLING_STAFF',
                                          'RECEPTIONIST','WARD_MANAGER','LAB_TECH',
                                          'PHARMACIST','RADIOLOGIST','SUPER_ADMIN')),
    specialisation    VARCHAR(100),
    department        VARCHAR(100),
    email             VARCHAR(150) NOT NULL UNIQUE,
    phone             VARCHAR(20),
    license_number    VARCHAR(80),
    qualifications    TEXT,
    joining_date      DATE         NOT NULL,
    end_date          DATE,
    working_hours     VARCHAR(100),
    consultation_fee  NUMERIC(10,2),
    is_active         BOOLEAN      NOT NULL DEFAULT TRUE,
    password_hash     VARCHAR(255) NOT NULL,
    last_login        TIMESTAMPTZ,
    mfa_enabled       BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by        UUID
);

CREATE INDEX idx_staff_email  ON staff (email);
CREATE INDEX idx_staff_role   ON staff (role);
CREATE INDEX idx_staff_dept   ON staff (department);

-- Refresh token store
CREATE TABLE IF NOT EXISTS refresh_token (
    id          UUID        NOT NULL PRIMARY KEY,
    staff_id    UUID        NOT NULL REFERENCES staff (staff_id) ON DELETE CASCADE,
    token_hash  VARCHAR(255) NOT NULL UNIQUE,
    expires_at  TIMESTAMPTZ NOT NULL,
    revoked     BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ═══════════════════════════════════════════════════════════════════════════
-- V4__create_appointment_tables.sql
-- ═══════════════════════════════════════════════════════════════════════════

CREATE TABLE IF NOT EXISTS appointment (
    appointment_id   UUID        NOT NULL PRIMARY KEY,
    patient_id       UUID        NOT NULL REFERENCES patient (patient_id),
    doctor_id        UUID        NOT NULL REFERENCES staff (staff_id),
    scheduled_date   DATE        NOT NULL,
    scheduled_time   TIME        NOT NULL,
    duration_minutes INT         NOT NULL DEFAULT 20,
    appointment_type VARCHAR(30) NOT NULL
                         CHECK (appointment_type IN ('CONSULTATION','FOLLOW_UP','PROCEDURE',
                                                      'EMERGENCY','TELECONSULT')),
    visit_type       VARCHAR(10) NOT NULL DEFAULT 'OPD'
                         CHECK (visit_type IN ('OPD','IPD')),
    chief_complaint  TEXT,
    status           VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED'
                         CHECK (status IN ('SCHEDULED','CONFIRMED','ARRIVED','IN_PROGRESS',
                                            'COMPLETED','CANCELLED','NO_SHOW','RESCHEDULED')),
    priority         VARCHAR(10) NOT NULL DEFAULT 'NORMAL'
                         CHECK (priority IN ('NORMAL','URGENT','EMERGENCY')),
    notes            TEXT,
    created_by       UUID        NOT NULL,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_appt_patient     ON appointment (patient_id);
CREATE INDEX idx_appt_doctor_date ON appointment (doctor_id, scheduled_date);
CREATE INDEX idx_appt_status      ON appointment (status);
CREATE INDEX idx_appt_date        ON appointment (scheduled_date);

-- Doctor availability slots
CREATE TABLE IF NOT EXISTS doctor_availability (
    id            UUID        NOT NULL PRIMARY KEY,
    doctor_id     UUID        NOT NULL REFERENCES staff (staff_id),
    day_of_week   INT         NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    start_time    TIME        NOT NULL,
    end_time      TIME        NOT NULL,
    slot_duration INT         NOT NULL DEFAULT 20,
    max_patients  INT         NOT NULL DEFAULT 20,
    is_active     BOOLEAN     NOT NULL DEFAULT TRUE
);

-- ═══════════════════════════════════════════════════════════════════════════
-- V5__create_clinical_core_tables.sql
-- OPD Visit, IPD Admission, Encounter, Diagnosis, Vitals
-- ═══════════════════════════════════════════════════════════════════════════

-- OPD Visit
CREATE TABLE IF NOT EXISTS opd_visit (
    opd_visit_id     UUID        NOT NULL PRIMARY KEY,
    patient_id       UUID        NOT NULL REFERENCES patient (patient_id),
    appointment_id   UUID                 REFERENCES appointment (appointment_id),
    doctor_id        UUID        NOT NULL REFERENCES staff (staff_id),
    nurse_id         UUID                 REFERENCES staff (staff_id),
    visit_date       DATE        NOT NULL DEFAULT CURRENT_DATE,
    visit_number     VARCHAR(20) NOT NULL UNIQUE,
    chief_complaint  TEXT        NOT NULL,
    status           VARCHAR(20) NOT NULL DEFAULT 'WAITING'
                         CHECK (status IN ('WAITING','WITH_NURSE','WITH_DOCTOR','COMPLETED','REFERRED')),
    disposition_code VARCHAR(30)
                         CHECK (disposition_code IN ('DISCHARGED','REFERRED_IPD','FOLLOW_UP',
                                                      'REFERRED_SPECIALIST','REFERRED_EMERGENCY')),
    follow_up_date   DATE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_opd_patient   ON opd_visit (patient_id);
CREATE INDEX idx_opd_doctor    ON opd_visit (doctor_id, visit_date);
CREATE INDEX idx_opd_date      ON opd_visit (visit_date);

-- Ward
CREATE TABLE IF NOT EXISTS ward (
    ward_id       UUID        NOT NULL PRIMARY KEY,
    ward_name     VARCHAR(100) NOT NULL,
    ward_type     VARCHAR(30) NOT NULL
                      CHECK (ward_type IN ('GENERAL','ICU','HDU','MATERNITY','PAEDIATRIC',
                                            'EMERGENCY','SURGICAL','MEDICAL','ISOLATION')),
    total_beds    INT         NOT NULL,
    available_beds INT        NOT NULL,
    floor         VARCHAR(10),
    building      VARCHAR(50),
    is_active     BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Bed
CREATE TABLE IF NOT EXISTS bed (
    bed_id            UUID        NOT NULL PRIMARY KEY,
    ward_id           UUID        NOT NULL REFERENCES ward (ward_id),
    bed_number        VARCHAR(20) NOT NULL,
    bed_type          VARCHAR(30) NOT NULL
                          CHECK (bed_type IN ('STANDARD','ICU','HDU','ISOLATION','DELIVERY')),
    status            VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE'
                          CHECK (status IN ('AVAILABLE','OCCUPIED','RESERVED','MAINTENANCE','CLEANING')),
    last_sanitised_at TIMESTAMPTZ,
    UNIQUE (ward_id, bed_number)
);

-- IPD Admission
CREATE TABLE IF NOT EXISTS ipd_admission (
    admission_id            UUID        NOT NULL PRIMARY KEY,
    patient_id              UUID        NOT NULL REFERENCES patient (patient_id),
    doctor_id               UUID        NOT NULL REFERENCES staff (staff_id),
    ward_id                 UUID                 REFERENCES ward (ward_id),
    bed_id                  UUID                 REFERENCES bed (bed_id),
    admission_date          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    discharge_date          TIMESTAMPTZ,
    admission_type          VARCHAR(30) NOT NULL
                                CHECK (admission_type IN ('EMERGENCY','ELECTIVE','TRANSFER','MATERNITY')),
    admission_status        VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                                CHECK (admission_status IN ('ACTIVE','DISCHARGED','TRANSFERRED','ABSCONDED','DECEASED')),
    referred_by             VARCHAR(150),
    diagnosis_at_admission  TEXT,
    discharge_notes         TEXT,
    discharge_condition     VARCHAR(20)
                                CHECK (discharge_condition IN ('IMPROVED','STABLE','DETERIORATED','DECEASED','ABSCONDED')),
    created_at              TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ipd_patient   ON ipd_admission (patient_id);
CREATE INDEX idx_ipd_doctor    ON ipd_admission (doctor_id);
CREATE INDEX idx_ipd_ward      ON ipd_admission (ward_id);
CREATE INDEX idx_ipd_status    ON ipd_admission (admission_status);

-- Encounter (universal clinical note — links to OPD or IPD)
CREATE TABLE IF NOT EXISTS encounter (
    encounter_id              UUID        NOT NULL PRIMARY KEY,
    patient_id                UUID        NOT NULL REFERENCES patient (patient_id),
    doctor_id                 UUID        NOT NULL REFERENCES staff (staff_id),
    source_id                 UUID        NOT NULL,
    source_type               VARCHAR(20) NOT NULL
                                  CHECK (source_type IN ('OPD_VISIT','IPD_ADMISSION','EMERGENCY')),
    chief_complaint           TEXT,
    history_of_present_illness TEXT,
    physical_examination      TEXT,
    assessment                TEXT,
    plan                      TEXT,
    follow_up_instructions    TEXT,
    ai_risk_score             NUMERIC(5,2),
    ai_risk_level             VARCHAR(10)
                                  CHECK (ai_risk_level IN ('LOW','MEDIUM','HIGH','CRITICAL')),
    created_at                TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_encounter_patient ON encounter (patient_id);
CREATE INDEX idx_encounter_source  ON encounter (source_id, source_type);
CREATE INDEX idx_encounter_doctor  ON encounter (doctor_id);

-- Diagnosis
CREATE TABLE IF NOT EXISTS diagnosis (
    diagnosis_id   UUID        NOT NULL PRIMARY KEY,
    encounter_id   UUID        NOT NULL REFERENCES encounter (encounter_id),
    icd_code       VARCHAR(20),
    description    TEXT        NOT NULL,
    diagnosis_type VARCHAR(20) NOT NULL DEFAULT 'PRIMARY'
                       CHECK (diagnosis_type IN ('PRIMARY','SECONDARY','DIFFERENTIAL','COMORBIDITY')),
    diagnosis_date DATE        NOT NULL DEFAULT CURRENT_DATE,
    severity       VARCHAR(20)
                       CHECK (severity IN ('MILD','MODERATE','SEVERE','CRITICAL')),
    status         VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                       CHECK (status IN ('ACTIVE','RESOLVED','CHRONIC','RECURRENT')),
    confirmed_by   UUID                 REFERENCES staff (staff_id),
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_diagnosis_encounter ON diagnosis (encounter_id);
CREATE INDEX idx_diagnosis_icd       ON diagnosis (icd_code);

-- Vital Signs
CREATE TABLE IF NOT EXISTS vital_signs (
    vital_id            UUID        NOT NULL PRIMARY KEY,
    patient_id          UUID        NOT NULL REFERENCES patient (patient_id),
    encounter_id        UUID                 REFERENCES encounter (encounter_id),
    recorded_by         UUID        NOT NULL REFERENCES staff (staff_id),
    systolic_bp         INT,
    diastolic_bp        INT,
    heart_rate          INT,
    temperature         NUMERIC(4,1),
    respiratory_rate    INT,
    oxygen_saturation   NUMERIC(5,2),
    weight_kg           NUMERIC(6,2),
    height_cm           NUMERIC(5,1),
    bmi                 NUMERIC(5,2),
    blood_glucose       NUMERIC(6,2),
    news2_score         INT,
    recorded_at         TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_vitals_patient   ON vital_signs (patient_id, recorded_at DESC);
CREATE INDEX idx_vitals_encounter ON vital_signs (encounter_id);

-- ═══════════════════════════════════════════════════════════════════════════
-- V6__create_pharmacy_tables.sql
-- ═══════════════════════════════════════════════════════════════════════════

CREATE TABLE IF NOT EXISTS medication (
    medicine_id       UUID         NOT NULL PRIMARY KEY,
    generic_name      VARCHAR(200) NOT NULL,
    brand_name        VARCHAR(200),
    category          VARCHAR(80),
    dosage_form       VARCHAR(50)  NOT NULL
                          CHECK (dosage_form IN ('TABLET','CAPSULE','SYRUP','INJECTION',
                                                  'CREAM','DROPS','INHALER','SUPPOSITORY','PATCH')),
    strength          VARCHAR(50),
    manufacturer      VARCHAR(150),
    stock_quantity    INT          NOT NULL DEFAULT 0,
    reorder_threshold INT          NOT NULL DEFAULT 10,
    unit_price        NUMERIC(10,2) NOT NULL DEFAULT 0,
    is_controlled     BOOLEAN      NOT NULL DEFAULT FALSE,
    is_active         BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS prescription (
    prescription_id  UUID        NOT NULL PRIMARY KEY,
    encounter_id     UUID        NOT NULL REFERENCES encounter (encounter_id),
    patient_id       UUID        NOT NULL REFERENCES patient (patient_id),
    doctor_id        UUID        NOT NULL REFERENCES staff (staff_id),
    prescribed_date  DATE        NOT NULL DEFAULT CURRENT_DATE,
    status           VARCHAR(20) NOT NULL DEFAULT 'PENDING'
                         CHECK (status IN ('PENDING','DISPENSED','PARTIALLY_DISPENSED','CANCELLED')),
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS prescription_item (
    item_id          UUID        NOT NULL PRIMARY KEY,
    prescription_id  UUID        NOT NULL REFERENCES prescription (prescription_id),
    medicine_id      UUID        NOT NULL REFERENCES medication (medicine_id),
    dosage           VARCHAR(50) NOT NULL,
    frequency        VARCHAR(50) NOT NULL,
    duration         VARCHAR(50),
    route            VARCHAR(30)
                         CHECK (route IN ('ORAL','INTRAVENOUS','INTRAMUSCULAR',
                                           'SUBCUTANEOUS','TOPICAL','INHALATION','RECTAL','SUBLINGUAL')),
    instructions     TEXT,
    quantity         INT         NOT NULL DEFAULT 1
);

-- ═══════════════════════════════════════════════════════════════════════════
-- V7__create_laboratory_tables.sql
-- ═══════════════════════════════════════════════════════════════════════════

CREATE TABLE IF NOT EXISTS lab_order (
    lab_order_id    UUID        NOT NULL PRIMARY KEY,
    patient_id      UUID        NOT NULL REFERENCES patient (patient_id),
    encounter_id    UUID                 REFERENCES encounter (encounter_id),
    ordered_by      UUID        NOT NULL REFERENCES staff (staff_id),
    test_name       VARCHAR(200) NOT NULL,
    test_code       VARCHAR(50),
    category        VARCHAR(80),
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDING'
                        CHECK (status IN ('PENDING','SAMPLE_COLLECTED','IN_PROGRESS',
                                           'COMPLETED','CANCELLED','REJECTED')),
    priority        VARCHAR(10) NOT NULL DEFAULT 'ROUTINE'
                        CHECK (priority IN ('ROUTINE','URGENT','STAT')),
    ordered_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    sample_collected_at TIMESTAMPTZ,
    result_date     TIMESTAMPTZ,
    result_value    TEXT,
    result_unit     VARCHAR(50),
    reference_range VARCHAR(100),
    is_abnormal     BOOLEAN,
    notes           TEXT,
    reported_by     UUID                 REFERENCES staff (staff_id)
);

CREATE INDEX idx_lab_patient   ON lab_order (patient_id);
CREATE INDEX idx_lab_encounter ON lab_order (encounter_id);
CREATE INDEX idx_lab_status    ON lab_order (status);

-- ═══════════════════════════════════════════════════════════════════════════
-- V8__create_billing_tables.sql
-- ═══════════════════════════════════════════════════════════════════════════

CREATE TABLE IF NOT EXISTS invoice (
    invoice_id       UUID         NOT NULL PRIMARY KEY,
    invoice_number   VARCHAR(30)  NOT NULL UNIQUE,
    patient_id       UUID         NOT NULL REFERENCES patient (patient_id),
    encounter_id     UUID                  REFERENCES encounter (encounter_id),
    admission_id     UUID                  REFERENCES ipd_admission (admission_id),
    invoice_date     DATE         NOT NULL DEFAULT CURRENT_DATE,
    total_amount     NUMERIC(12,2) NOT NULL DEFAULT 0,
    paid_amount      NUMERIC(12,2) NOT NULL DEFAULT 0,
    balance_due      NUMERIC(12,2) GENERATED ALWAYS AS (total_amount - paid_amount) STORED,
    payment_status   VARCHAR(20)  NOT NULL DEFAULT 'UNPAID'
                         CHECK (payment_status IN ('UNPAID','PARTIALLY_PAID','PAID','WAIVED','WRITTEN_OFF')),
    payment_method   VARCHAR(30)
                         CHECK (payment_method IN ('CASH','CARD','MOBILE_MONEY','INSURANCE',
                                                    'BANK_TRANSFER','ONLINE')),
    insurance_claimed  BOOLEAN    NOT NULL DEFAULT FALSE,
    insurance_amount   NUMERIC(12,2),
    ai_denial_risk_score NUMERIC(5,2),
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_invoice_patient ON invoice (patient_id);
CREATE INDEX idx_invoice_status  ON invoice (payment_status);
CREATE INDEX idx_invoice_date    ON invoice (invoice_date);

CREATE TABLE IF NOT EXISTS invoice_line_item (
    line_item_id   UUID         NOT NULL PRIMARY KEY,
    invoice_id     UUID         NOT NULL REFERENCES invoice (invoice_id),
    description    VARCHAR(200) NOT NULL,
    category       VARCHAR(50)  NOT NULL
                       CHECK (category IN ('CONSULTATION','LAB','PHARMACY','PROCEDURE',
                                            'BED_CHARGE','NURSING','IMAGING','SUPPLIES','OTHER')),
    quantity       INT          NOT NULL DEFAULT 1,
    unit_price     NUMERIC(10,2) NOT NULL,
    total_price    NUMERIC(12,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    cpt_code       VARCHAR(20),
    icd_code       VARCHAR(20),
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS insurance_claim (
    claim_id          UUID         NOT NULL PRIMARY KEY,
    invoice_id        UUID         NOT NULL REFERENCES invoice (invoice_id),
    patient_id        UUID         NOT NULL REFERENCES patient (patient_id),
    insurance_provider VARCHAR(150) NOT NULL,
    policy_number     VARCHAR(80)  NOT NULL,
    claim_amount      NUMERIC(12,2) NOT NULL,
    approved_amount   NUMERIC(12,2),
    claim_status      VARCHAR(30)  NOT NULL DEFAULT 'DRAFT'
                          CHECK (claim_status IN ('DRAFT','SUBMITTED','UNDER_REVIEW','APPROVED',
                                                   'PARTIALLY_APPROVED','DENIED','APPEALED','CLOSED')),
    submission_date   DATE,
    response_date     DATE,
    denial_reason     TEXT,
    icd_codes         TEXT,
    cpt_codes         TEXT,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- ═══════════════════════════════════════════════════════════════════════════
-- V9__create_engagement_tables.sql
-- ═══════════════════════════════════════════════════════════════════════════

CREATE TABLE IF NOT EXISTS notification (
    notification_id  UUID        NOT NULL PRIMARY KEY,
    patient_id       UUID                 REFERENCES patient (patient_id),
    staff_id         UUID                 REFERENCES staff (staff_id),
    channel          VARCHAR(20) NOT NULL
                         CHECK (channel IN ('SMS','EMAIL','PUSH','IN_APP')),
    notification_type VARCHAR(50) NOT NULL,
    subject          VARCHAR(200),
    body             TEXT        NOT NULL,
    status           VARCHAR(20) NOT NULL DEFAULT 'PENDING'
                         CHECK (status IN ('PENDING','SENT','DELIVERED','FAILED','READ')),
    scheduled_at     TIMESTAMPTZ,
    sent_at          TIMESTAMPTZ,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_notif_patient ON notification (patient_id);
CREATE INDEX idx_notif_status  ON notification (status, scheduled_at);

-- ═══════════════════════════════════════════════════════════════════════════
-- V10__seed_reference_data.sql
-- Seed essential reference data for development
-- ═══════════════════════════════════════════════════════════════════════════

-- Default admin user (password: Admin@123 — change immediately)
INSERT INTO staff (staff_id, employee_number, first_name, last_name, role,
                   department, email, phone, joining_date, is_active, password_hash)
VALUES (
    gen_random_uuid(),
    'EMP-0001',
    'System',
    'Administrator',
    'SUPER_ADMIN',
    'IT',
    'admin@hms.local',
    '+0000000000',
    CURRENT_DATE,
    TRUE,
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewFnERfkgD3/KiKe'  -- Admin@123 BCrypt
)
ON CONFLICT DO NOTHING;

-- Sample wards
INSERT INTO ward (ward_id, ward_name, ward_type, total_beds, available_beds, floor, building)
VALUES
    (gen_random_uuid(), 'General Ward A',  'GENERAL',   30, 30, '1', 'Main'),
    (gen_random_uuid(), 'ICU',             'ICU',        8,  8, '2', 'Main'),
    (gen_random_uuid(), 'Emergency Ward',  'EMERGENCY', 20, 20, 'G', 'Main'),
    (gen_random_uuid(), 'Maternity Ward',  'MATERNITY', 15, 15, '2', 'Wing B'),
    (gen_random_uuid(), 'Paediatric Ward', 'PAEDIATRIC',20, 20, '3', 'Wing B')
ON CONFLICT DO NOTHING;
