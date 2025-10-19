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

-- ==========================================
-- Insert default admin user (optional)
-- ==========================================
INSERT INTO users (id, email, password, nom, prenom, telephone, user_type, created_at, updated_at)
VALUES (
           1,
           'admin@brain2build.com',
           '$2a$10$DUMMY_HASH', -- tu mettras ici un vrai mot de passe encodé BCrypt
           'System',
           'Admin',
           '0000000000',
           'ADMIN',
           NOW(),
           NOW()
       )
    ON CONFLICT DO NOTHING;

-- ==========================================
-- Link admin user to ROLE_ADMIN
-- ==========================================
INSERT INTO user_role (user_id, role_id)
VALUES (1, 1)
    ON CONFLICT DO NOTHING;
