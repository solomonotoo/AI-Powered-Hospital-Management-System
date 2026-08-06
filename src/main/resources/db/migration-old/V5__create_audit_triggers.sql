
-- =============================================================================
-- AUDIT TRIGGER FUNCTION
-- Automatically updates updated_at on every row update
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Attach trigger to facility
CREATE TRIGGER trg_facility_updated_at
    BEFORE UPDATE ON facility
    FOR EACH ROW
    EXECUTE FUNCTION fn_set_updated_at();

-- Attach trigger to district
CREATE TRIGGER trg_district_updated_at
    BEFORE UPDATE ON district
    FOR EACH ROW
    EXECUTE FUNCTION fn_set_updated_at();

    
