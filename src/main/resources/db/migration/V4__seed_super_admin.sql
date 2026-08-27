-- V4__seed_super_admin.sql

-- Stable UUID for the system administrator
-- This same UUID is used by staff and user_credentials.

INSERT INTO staff (
    id,
    employee_number,
    first_name,
    last_name,
    role,
    work_email,
    joining_date,
    is_active,
    created_at,
    updated_at,
    created_by,
    updated_by
)
VALUES (
    '3fafffbb-05ac-4999-9403-914062d4d549',
    'EMP-0000',
    'System',
    'Administrator',
    'SUPER_ADMIN',
    'admin@hms.local',
    CURRENT_DATE,
    TRUE,
    NOW(),
    NOW(),
    '3fafffbb-05ac-4999-9403-914062d4d549',
    NULL
);

INSERT INTO user_credentials (
    staff_id,
    login_email,
    password_hash,
    mfa_enabled,
    must_change_password,
    is_active,
    created_at,
    updated_at,
    created_by,
    updated_by
)
VALUES (
    '3fafffbb-05ac-4999-9403-914062d4d549',
    'admin@hms.local',
    '$2a$12$RPWLSne.NIYJMcJDd/C.xOsueW1VX5d/V0ybSe.URzaTYDrG6hhFS',
    FALSE,
    TRUE,
    TRUE,
    NOW(),
    NOW(),
    '3fafffbb-05ac-4999-9403-914062d4d549',
    NULL
);
