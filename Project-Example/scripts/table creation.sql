--let's build a one-to-many multiplicity between users and tasks
CREATE TABLE IF NOT EXISTS users (
	user_id SERIAL PRIMARY KEY,
	first_name VARCHAR(40),
	last_name CHAR(40),
	username VARCHAR(40) UNIQUE,
	password VARCHAR(40) NOT NULL
);

CREATE UNIQUE INDEX users_username ON users (username);

CREATE TABLE tasks (
	task_id SERIAL PRIMARY KEY,
	title VARCHAR(240),
	description VARCHAR(8000),
	completed BOOLEAN,
	user_id INTEGER,
	CONSTRAINT fk_tasks_users FOREIGN KEY (user_id) REFERENCES users (user_id)
);
