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
    '7f3a9c2e-6d41-4b85-a2f7-91c0e5d8b634',
    'SUPER_ADMIN_ROLE',
    'Full system access',
    TRUE,
    NOW(),
    NOW(),
    '3b8f1d72-5c94-4e26-a7b1-62d9f04c8135'
);

INSERT INTO roles (id, name, description, system_defined, created_at, updated_at, created_by)
VALUES (
    'c5e8124b-39a7-4f60-bd23-8e1a6c97f452',
    'RECEPTIONIST_ROLE',
    'Front-desk patient registration',
    TRUE,
    NOW(),
    NOW(),
    '3b8f1d72-5c94-4e26-a7b1-62d9f04c8135'
);
