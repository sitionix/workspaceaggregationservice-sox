ALTER TABLE workspace_site_meta
    ADD CONSTRAINT fk_workspace_site_meta_type
        FOREIGN KEY (type_id) REFERENCES workspace_site_meta_types (id);
