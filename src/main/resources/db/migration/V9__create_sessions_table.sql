
CREATE TABLE user_sessions (
    id UUID PRIMARY KEY,
    staff_id UUID NOT NULL,
    issued_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    user_agent VARCHAR(255),
    revoked BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_user_sessions_staff FOREIGN KEY (staff_id) REFERENCES staff(id)
);

CREATE INDEX idx_user_sessions_staff_id ON user_sessions(staff_id);

--The index on staff_id matters here — findByStaffId is called on every 
--GET /users/{userId}/sessions and (indirectly, via ownership checks) is 
--a natural query pattern; without an index, that becomes a full table 
--scan as session history grows across every staff member and every login 
--they've ever made.