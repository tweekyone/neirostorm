CREATE TABLE users
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP NOT NULL
);

CREATE TABLE practices
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT NOT NULL,
    steps         TEXT,
    example       TEXT,
    conclusion    TEXT,
    user_id       INTEGER,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    topic         VARCHAR(50) CHECK (topic IN ('DEVELOPMENT', 'ANALYSIS', 'TESTING', 'DOCUMENTATION', 'DEVOPS')),
    preview_image TEXT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);