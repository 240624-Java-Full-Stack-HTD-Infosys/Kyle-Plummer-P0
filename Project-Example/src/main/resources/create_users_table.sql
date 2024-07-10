DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
	user_id SERIAL PRIMARY KEY,
	first_name VARCHAR(40),
	last_name CHAR(40),
	username VARCHAR(40) UNIQUE,
	password VARCHAR(40) NOT NULL
);