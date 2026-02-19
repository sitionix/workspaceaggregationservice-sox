INSERT INTO workspace_site_meta_types (id, description)
VALUES (1, 'PORTFOLIO'),
       (2, 'BUSINESS'),
       (3, 'BLOG'),
       (4, 'STORE'),
       (5, 'LANDING'),
       (6, 'OTHER')
ON CONFLICT (id) DO NOTHING;
