INSERT INTO workspace_site_meta_statuses (id, description)
VALUES (1, 'DRAFT'),
       (2, 'PUBLISHED'),
       (3, 'ARCHIVED')
ON CONFLICT (id) DO NOTHING;
