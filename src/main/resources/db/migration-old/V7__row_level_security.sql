-- =============================================================================
-- ROW LEVEL SECURITY (Multi-Tenant Isolation)
-- Ensures no facility can ever read another facility's data
-- =============================================================================
ALTER TABLE facility ENABLE ROW LEVEL SECURITY;

-- Policy: each tenant only sees its own row
CREATE POLICY tenant_isolation_policy ON facility
    USING (current_setting('app.current_tenant_id', true) IS NOT NULL
    AND tenant_id = current_setting('app.current_tenant_id')::UUID
    );