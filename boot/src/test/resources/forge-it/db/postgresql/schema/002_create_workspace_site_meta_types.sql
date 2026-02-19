CREATE TABLE IF NOT EXISTS workspace_site_meta_types (
    id BIGINT PRIMARY KEY,
    description VARCHAR(64) NOT NULL UNIQUE
);
