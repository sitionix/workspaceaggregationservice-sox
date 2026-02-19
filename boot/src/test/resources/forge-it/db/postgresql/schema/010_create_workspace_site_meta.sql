CREATE TABLE IF NOT EXISTS workspace_site_meta (
    site_id UUID PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(60),
    status_id BIGINT NOT NULL,
    type_id BIGINT,
    description VARCHAR(160),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);
