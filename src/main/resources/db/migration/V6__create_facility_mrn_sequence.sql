-- ═══════════════════════════════════════════════════════════════════════════
-- V4__facility_mrn_sequence.sql
-- sequence table for facility mrn
-- ═══════════════════════════════════════════════════════════════════════════


CREATE TABLE facility_mrn_sequence (
    facility_code VARCHAR(10) PRIMARY KEY,
    last_value BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_facility_mrn_sequence
        FOREIGN KEY (facility_code)
        REFERENCES facilities(facility_code)
);