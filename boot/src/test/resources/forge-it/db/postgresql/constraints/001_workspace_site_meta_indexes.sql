CREATE INDEX IF NOT EXISTS idx_workspace_site_meta_owner_updated
    ON workspace_site_meta (owner_user_id, updated_at DESC);
