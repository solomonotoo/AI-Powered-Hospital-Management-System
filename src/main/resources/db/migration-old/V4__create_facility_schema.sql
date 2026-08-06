
-- =============================================================================
-- FACILITY TABLE
-- Facility IS the tenant — facility_id == tenant_id
-- =============================================================================
CREATE TABLE IF NOT EXISTS facility (
    facility_id         UUID                NOT NULL,
    tenant_id           UUID                NOT NULL,
    district_id         UUID                NOT NULL,
    facility_name       VARCHAR(200)        NOT NULL,
    facility_type       facility_type       NOT NULL,
    subscription_plan   subscription_plan   NOT NULL,
    active              BOOLEAN             NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ         NOT NULL DEFAULT NOW(),

	PRIMARY KEY (facility_id),
    
    CONSTRAINT uq_facility_tenant UNIQUE (tenant_id),
    CONSTRAINT uq_facility_name UNIQUE (facility_name),

    CONSTRAINT fk_facility_district
        FOREIGN KEY (district_id)
        REFERENCES district (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

   
    -- facility_id and tenant_id are always the same UUID
     CONSTRAINT chk_facility_tenant_match
        CHECK (facility_id = tenant_id)
);


-- =============================================================================
-- INDEXES
-- =============================================================================

-- Fast tenant resolution on every request
CREATE INDEX idx_facility_tenant_id
    ON facility (tenant_id);

-- Filter active facilities
CREATE INDEX idx_facility_active
    ON facility (active);

-- Filter by type
CREATE INDEX idx_facility_type
    ON facility (facility_type);

-- Filter by subscription plan
CREATE INDEX idx_facility_subscription_plan
    ON facility (subscription_plan);

-- Filter by district
CREATE INDEX idx_facility_district_id
    ON facility (district_id);

-- Composite — active facilities by type (common query)
CREATE INDEX idx_facility_active_type
    ON facility (active, facility_type);
