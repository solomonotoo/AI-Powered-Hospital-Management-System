
CREATE TABLE staff(
	id UUID PRIMARY KEY,
	employee_number VARCHAR(30) NOT NULL UNIQUE,

	first_name VARCHAR(45) NOT NULL,
	last_name VARCHAR(45) NOT NULL,
	maiden_name VARCHAR(45),
	preferred_name VARCHAR(45),

	role VARCHAR(30) NOT NULL,
	specialisation VARCHAR(100),
	department VARCHAR(100),

	work_email VARCHAR(150) NOT NULL UNIQUE,
	phone VARCHAR(20),

	license_number VARCHAR(80),
	qualifications TEXT,

	joining_date DATE NOT NULL,
	end_date DATE,
	working_hours VARCHAR(100),
	consultation_fee NUMERIC(10,2),

	is_active BOOLEAN NOT NULL DEFAULT true,

	created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    created_by UUID NOT NULL,
    updated_by UUID
);