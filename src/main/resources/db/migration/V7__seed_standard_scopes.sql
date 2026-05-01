
INSERT INTO scopes (name) VALUES 
('admin:all'),
('attendance:read'),
('attendance:write'),
('student:read'),
('profile:write'),
('student:promote')
ON CONFLICT (name) DO NOTHING;
