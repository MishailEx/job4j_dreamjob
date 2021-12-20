CREATE TABLE post (
    id   INT IDENTITY PRIMARY KEY ,
    name VARCHAR (100)
);

CREATE TABLE candidate (
    id   INT IDENTITY PRIMARY KEY ,
    name VARCHAR (100)
);

CREATE TABLE users (
    id       INT IDENTITY PRIMARY KEY ,
    name     VARCHAR (100),
    email    VARCHAR (100),
    password VARCHAR (100),
    UNIQUE (email)
);