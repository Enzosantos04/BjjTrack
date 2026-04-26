CREATE TABLE attendance (
id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
student_id BIGINT NOT NULL,
attendance_date DATE NOT NULL,
created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_attendance_student
FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
CONSTRAINT uq_attendance_student_day
    UNIQUE (student_id, attendance_date)
);

CREATE INDEX idx_attendance_date ON attendance(attendance_date);