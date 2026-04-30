CREATE TABLE scopes (
id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users_scopes(
user_id BIGINT NOT NULL,
scope_id BIGINT NOT NULL,
PRIMARY KEY (user_id, scope_id),
CONSTRAINT fk_users_scopes_user FOREIGN KEY (user_id) references users(id) ON DELETE CASCADE,
CONSTRAINT fk_users_scopes_scope FOREIGN KEY (scope_id) references scopes(id) ON DELETE CASCADE
)