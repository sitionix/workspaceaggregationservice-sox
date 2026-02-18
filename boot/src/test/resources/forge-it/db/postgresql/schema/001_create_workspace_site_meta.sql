CREATE TABLE IF NOT EXISTS workspace_site_meta (
    site_id UUID PRIMARY KEY,
    owner_user_id BIGINT NOT NULL,
    name VARCHAR(60),
    status VARCHAR(32),
    type VARCHAR(32),
    description VARCHAR(160),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);
