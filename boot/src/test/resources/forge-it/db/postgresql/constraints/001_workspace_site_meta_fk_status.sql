ALTER TABLE workspace_site_meta
    ADD CONSTRAINT fk_workspace_site_meta_status
        FOREIGN KEY (status_id) REFERENCES workspace_site_meta_statuses (id);
