INSERT INTO forge_inbox_statuses (id, description)
VALUES (1, 'PENDING'),
       (2, 'IN_PROGRESS'),
       (3, 'PROCESSED'),
       (4, 'FAILED'),
       (5, 'DEAD')
ON CONFLICT (id) DO UPDATE SET description = EXCLUDED.description;
