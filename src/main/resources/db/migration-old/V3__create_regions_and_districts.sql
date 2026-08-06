-- =============================================================================
-- REGIONS TABLE
-- =============================================================================

CREATE TABLE IF NOT EXISTS region (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    region_name VARCHAR(100) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uk_region_name UNIQUE (region_name)
);

-- =============================================================================
-- DISTRICTS TABLE
-- =============================================================================

CREATE TABLE IF NOT EXISTS district (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    district_name VARCHAR(100) NOT NULL,
    region_id UUID NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_district_region
        FOREIGN KEY (region_id)
        REFERENCES region (id)
        ON DELETE CASCADE,

    CONSTRAINT uk_district_region_name
        UNIQUE (region_id, district_name)
);

-- =============================================================================
-- INDEXES
-- =============================================================================

CREATE INDEX idx_district_region_id
ON district(region_id);