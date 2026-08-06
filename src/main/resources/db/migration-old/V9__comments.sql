
-- =============================================================================
-- COMMENTS
-- =============================================================================
COMMENT ON TABLE  facility                      IS 'Each facility is a tenant. facility_id = tenant_id always.';
COMMENT ON COLUMN facility.facility_id          IS 'Primary key — also serves as the tenant identifier.';
COMMENT ON COLUMN facility.tenant_id            IS 'Redundant with facility_id. Kept for explicit multi-tenant filtering.';
COMMENT ON COLUMN facility.district_id          IS 'FK to district table. Identifies the administrative district.';
COMMENT ON COLUMN facility.facility_name        IS 'Official name of the health facility. Must be unique.';
COMMENT ON COLUMN facility.facility_type        IS 'Classification: CHPS, HEALTH_CENTRE, DISTRICT_HOSPITAL etc.';
COMMENT ON COLUMN facility.subscription_plan    IS 'Billing tier: STARTER, STANDARD, ENTERPRISE, GOVERNMENT.';
COMMENT ON COLUMN facility.active               IS 'Soft delete flag. False means deactivated, not physically deleted.';
COMMENT ON COLUMN facility.created_at           IS 'Timestamp of record creation. Never updated.';
COMMENT ON COLUMN facility.updated_at           IS 'Timestamp of last update. Managed by trigger.';

COMMENT ON TABLE  district                      IS 'Administrative districts. Seeded at startup.';