CREATE INDEX IF NOT EXISTS idx_forge_inbox_events_status_id
    ON forge_inbox_events (status_id);

CREATE INDEX IF NOT EXISTS idx_forge_inbox_events_event_type
    ON forge_inbox_events (event_type);

CREATE INDEX IF NOT EXISTS idx_forge_inbox_events_polling
    ON forge_inbox_events (status_id, next_retry_at, created_at);

CREATE INDEX IF NOT EXISTS idx_forge_inbox_events_lock_until
    ON forge_inbox_events (lock_until);

CREATE UNIQUE INDEX IF NOT EXISTS uq_forge_inbox_events_idempotency_key
    ON forge_inbox_events (idempotency_key);
