CREATE TABLE IF NOT EXISTS forge_inbox_events (
    id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    payload TEXT NOT NULL,
    headers JSONB NOT NULL DEFAULT '{}'::jsonb,
    metadata JSONB NOT NULL DEFAULT '{}'::jsonb,
    trace_id VARCHAR(255),
    idempotency_key VARCHAR(255) NOT NULL,
    aggregate_type_id BIGINT,
    aggregate_id BIGINT,
    initiator_type VARCHAR(255),
    initiator_id VARCHAR(255),
    status_id BIGINT NOT NULL,
    retry_count INT NOT NULL,
    next_retry_at TIMESTAMPTZ NOT NULL,
    last_error TEXT,
    lock_until TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_forge_inbox_events_aggregate_type_id
        FOREIGN KEY (aggregate_type_id) REFERENCES forge_inbox_aggregate_types (id),
    CONSTRAINT fk_forge_inbox_events_status_id
        FOREIGN KEY (status_id) REFERENCES forge_inbox_statuses (id)
);
