CREATE TABLE city
(
    id   INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE post
(
    id      INT IDENTITY PRIMARY KEY,
    name    VARCHAR(100),
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE candidate
(
    id      INT IDENTITY PRIMARY KEY,
    name    VARCHAR(100),
    city_id INT,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users
(
    id       INT IDENTITY PRIMARY KEY,
    name     VARCHAR(100),
    email    VARCHAR(100),
    password VARCHAR(100),
    UNIQUE (email)
);

