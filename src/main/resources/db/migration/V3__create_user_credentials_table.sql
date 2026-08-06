

CREATE TABLE user_credentials(
	staff_id UUID PRIMARY KEY,
	login_email VARCHAR(150) NOT NULL,
	password_hash VARCHAR(255) NOT NULL,
	mfa_enabled BOOLEAN NOT NULL DEFAULT FALSE,
	must_change_password BOOLEAN NOT NULL DEFAULT TRUE,
	last_login_at TIMESTAMP,
	is_active BOOLEAN NOT NULL DEFAULT TRUE,

	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP ,
	created_by UUID NOT NULL,
	updated_by UUID ,

	CONSTRAINT fk_user_credentials_staff FOREIGN KEY (staff_id) REFERENCES staff(id)
);

-- Note the FOREIGN KEY here — this is deliberate and worth flagging: at the domain/module 
-- level, identity depends on staff only through the StaffLookup API (no direct domain coupling), 
-- which is correct for Modulith boundaries. But at the database level, a physical FK constraint is 
-- still good practice for referential integrity, since both tables live in the same physical database 
-- (single-tenant, single schema). This isn't a contradiction — module boundaries are a code-organization 
-- concept; the DB schema can still enforce integrity across what are logically two separate bounded contexts
--  sharing one database instance.