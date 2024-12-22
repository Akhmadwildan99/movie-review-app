CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY  NOT NULL,
    username VARCHAR(50),
    email VARCHAR(50),
    password TEXT,
    is_admin BOOLEAN NOT NULL,
    is_active BOOLEAN NOT NULL ,
    created_at TIMESTAMP WITHOUT TIME ZONE ,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);