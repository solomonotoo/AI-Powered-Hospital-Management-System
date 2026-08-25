
CREATE TABLE roles(
	id UUID PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	description VARCHAR(255),
	system_defined BOOLEAN NOT NULL DEFAULT FALSE,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP,
	created_by UUID NOT NULL,
	updated_by UUID
);

CREATE TABLE role_permissions(
	role_id UUID NOT NULL REFERENCES roles(id) NO DELETE CASCADE,
	permission_code VARCHAR(100) NOT NULL,
	PRIMARY KEY (role_id, permisson_code)
);

CREATE TABLE permissions(
	code VARCHAR(100) PRIMARY KEY,
	description VARCHAR(255)
	
);

CREATE TABLE role_assignments(
	id UUID PRIMARY KEY,
	staff_id UUID NOT NULL REFERENCES staff(id),
	role_id UUID NOT NULL REFERENCES roles(id),
	expires_at TIMESTAMP,
	revoked BOOLEAN NOT NULL DEFAULT FALSE,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_by UUID NOT NULL,
	updated_by UUID 
);


CREATE INDEX idx_role_assignment_staff_id ON role_assignments(staff_id);

CREATE TABLE user_activity(
	id UUID PRIMARY KEY,
	staff_id UUID NOT NULL REFERENCES staff(id),
	event_type VARCHAR(50) NOT NULL,
	actor_id UUID,
	occured_at TIMESTAMP NOT NULL
 );

CREATE INDEX idx_user_activity_staff_id ON user_activity(staff_id, occured_at DESC);


