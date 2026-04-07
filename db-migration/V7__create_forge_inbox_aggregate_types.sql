CREATE TABLE IF NOT EXISTS forge_inbox_aggregate_types (
    id BIGINT PRIMARY KEY,
    description VARCHAR(255) NOT NULL UNIQUE
);
