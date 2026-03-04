
CREATE TABLE student(
id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
user_id BIGINT NOT NULL UNIQUE,
academy_id BIGINT NOT NULL,
birth_date DATE NOT NULL,
belt VARCHAR(50) NOT NULL DEFAULT 'WHITE',
last_promotion DATE,
created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_student_academy FOREIGN KEY (academy_id) references academy(id) ON DELETE CASCADE,
CONSTRAINT fk_student_users FOREIGN KEY (user_id) references users(id) ON DELETE CASCADE

);
CREATE INDEX idx_student_academy_id ON student(academy_id);