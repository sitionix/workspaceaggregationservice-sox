CREATE TABLE IF NOT EXISTS workspace_site_meta_statuses (
    id BIGINT PRIMARY KEY,
    code VARCHAR(32) NOT NULL UNIQUE
);
