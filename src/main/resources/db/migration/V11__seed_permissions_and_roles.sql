INSERT INTO permissions (code, description) VALUES
    ('PATIENT_READ', 'View patient records'),
    ('PATIENT_WRITE', 'Register and update patient records'),
    ('FACILITY_READ', 'View facility information'),
    ('FACILITY_MANAGE', 'Onboard, update, deactivate facilities'),
    ('STAFF_READ', 'View staff records'),
    ('STAFF_MANAGE', 'Onboard, update staff records'),
    ('ROLE_MANAGE', 'Create and modify roles, assign roles to users'),
    ('USER_MANAGE', 'Manage user credentials and sessions'),
    ('BILLING_READ', 'View billing information'),
    ('BILLING_MANAGE', 'Manage billing and invoices');

INSERT INTO roles (id, name, description, system_defined, created_at, updated_at, created_by)
VALUES (
    '00000000-0000-0000-0000-000000000010',
    'SUPER_ADMIN_ROLE', 'Full system access', TRUE, NOW(), NOW(),
    '00000000-0000-0000-0000-000000000001'
);
INSERT INTO role_permissions (role_id, permission_code)
SELECT '00000000-0000-0000-0000-000000000010', code FROM permissions;

INSERT INTO roles (id, name, description, system_defined, created_at, updated_at, created_by)
VALUES (
    '00000000-0000-0000-0000-000000000011',
    'RECEPTIONIST_ROLE', 'Front-desk patient registration', TRUE, NOW(), NOW(),
    '00000000-0000-0000-0000-000000000001'
);
INSERT INTO role_permissions (role_id, permission_code) VALUES
    ('00000000-0000-0000-0000-000000000011', 'PATIENT_READ'),
    ('00000000-0000-0000-0000-000000000011', 'PATIENT_WRITE'),
    ('00000000-0000-0000-0000-000000000011', 'FACILITY_READ');