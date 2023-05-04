DROP TABLE IF EXISTS practices;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP NOT NULL
);

CREATE TABLE practices
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT NOT NULL,
    steps         TEXT,
    example       TEXT,
    conclusion    TEXT,
    user_id       INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    topic         VARCHAR(50) CHECK (topic IN ('DEVELOPMENT', 'ANALYSIS', 'TESTING', 'DOCUMENTATION', 'DEVOPS')),
    preview_image TEXT
);

INSERT INTO users (email, password_hash, name, created_at)
VALUES ('example@example.com', 'encrypted_password_hash', 'John Doe', NOW());

INSERT INTO practices (title, description, topic, user_id)
VALUES ('Создание Dockerfile для Spring Boot приложения, собранного при помощи Gradle',
        'Использование ChatGPT для создания Dockerfile для Spring Boot приложения. На выходе получаем готовый Dockerfile, который используется для создания Docker образа и последующего развертывания приложения.',
        'DevOps', 1),
       ('Формирование логического описание БД на основе DDL (Quick documentation idea)',
        'Если уже есть база данных, и в ней присутствую какие-то записи, то можно методом Reverse Engineering получить логическое описание БД / Таблицы с помощью ChatGPT;',
        'Документирование', 1);