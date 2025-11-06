-- ==========================================
-- Insert default roles for Brain2Build
-- ==========================================
INSERT INTO roles (id, nom)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_IDEATOR'),
    (3, 'ROLE_WORKER'),
    (4, 'ROLE_USER')
    ON CONFLICT DO NOTHING;
