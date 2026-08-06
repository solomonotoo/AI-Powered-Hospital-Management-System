
-- =============================================================================
-- ENUMS
-- =============================================================================
CREATE TYPE facility_type AS ENUM (
    'CHPS',
    'HEALTH_CENTRE',
    'POLYCLINIC',
    'DISTRICT_HOSPITAL',
    'REGIONAL_HOSPITAL',
    'TEACHING_HOSPITAL',
    'PRIVATE_CLINIC'
);

CREATE TYPE subscription_plan AS ENUM (
    'STARTER',
    'STANDARD',
    'ENTERPRISE',
    'GOVERNMENT'
);