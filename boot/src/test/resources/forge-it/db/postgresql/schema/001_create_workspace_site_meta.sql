CREATE TABLE IF NOT EXISTS workspace_site_meta_statuses (
    id BIGINT PRIMARY KEY,
    code VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS workspace_site_meta_types (
    id BIGINT PRIMARY KEY,
    code VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS workspace_site_meta (
    site_id UUID PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(60),
    status_id BIGINT NOT NULL REFERENCES workspace_site_meta_statuses (id),
    type_id BIGINT REFERENCES workspace_site_meta_types (id),
    description VARCHAR(160),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

INSERT INTO workspace_site_meta_statuses (id, code)
VALUES (1, 'DRAFT'),
       (2, 'PUBLISHED'),
       (3, 'ARCHIVED')
ON CONFLICT (id) DO NOTHING;

INSERT INTO workspace_site_meta_types (id, code)
VALUES (1, 'PORTFOLIO'),
       (2, 'BUSINESS'),
       (3, 'BLOG'),
       (4, 'STORE'),
       (5, 'LANDING'),
       (6, 'OTHER')
ON CONFLICT (id) DO NOTHING;
