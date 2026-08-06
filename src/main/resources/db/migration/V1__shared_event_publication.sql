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

-- Index for retry/cleanup (important for background processing)
CREATE INDEX idx_event_pub_completion ON event_publication (completion_date)
    WHERE completion_date IS NULL;

-- Additional performance index for publishing order
CREATE INDEX idx_event_pub_unpublished
ON event_publication (publication_date)
WHERE completion_date IS NULL;
    
