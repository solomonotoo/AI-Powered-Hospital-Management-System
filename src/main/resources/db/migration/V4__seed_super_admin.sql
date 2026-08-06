-- Bootstrap seed: creates exactly one SUPER_ADMIN staff record and credential,
-- so the system has an initial account to log in with before any other
-- admin-driven provisioning is possible.
--
-- Login email: admin@hms.local
-- Temporary password: ChangeMe123!
-- (This is a widely-known placeholder BCrypt hash for "ChangeMe123!" — CHANGE IT
--  IMMEDIATELY after first login. mustChangePassword is already TRUE, but that
--  flag only affects application-level enforcement — enforce it in the actual
--  login/change-password flow before relying on it as a security control.)

INSERT INTO staff(
	id,employee_number, first_name,last_name, role, work_email, 
	joining_date, is_active,created_at,updated_at,
	created_by,updated_by
) VALUES(
	'3fafffbb-05ac-4999-9403-914062d4d549',
	'EMP-0000',
    'System',
    'Administrator',
    'SUPER_ADMIN',
    'admin@hms.local',
	CURRENT_DATE,
	TRUE,
	NOW(), NOW(),
	'3fafffbb-05ac-4999-9403-914062d4d549',
	NULL
	);

INSERT INTO user_credentials(
	staff_id, login_email, password_hash, mfa_enabled, must_change_password,
	is_active, created_at, updated_at, created_by, updated_by
) VALUES (
	'3fafffbb-05ac-4999-9403-914062d4d549',
	'admin@hms.local',
	'$2a$12$pro.ILKqVdTfl7PsTEXrtuwMSI.SeLycmGxlnuQRTkZOZPcn.XkHe',
	FALSE,
	TRUE,
	TRUE,
	NOW(), NOW(),
	'3fafffbb-05ac-4999-9403-914062d4d549',
	NULL
);
