CREATE INDEX IF NOT EXISTS idx_workspace_site_meta_user_updated
    ON workspace_site_meta (user_id, updated_at DESC);
