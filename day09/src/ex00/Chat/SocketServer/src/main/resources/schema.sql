DROP TABLE IF EXISTS Users CASCADE;

CREATE TABLE IF NOT EXISTS Users (
        id SERIAL PRIMARY KEY NOT null,
        userName VARCHAR NOT null,
        password VARCHAR);
