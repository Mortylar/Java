DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Messages CASCADE;

CREATE TABLE IF NOT EXISTS Users (
        id SERIAL PRIMARY KEY NOT null,
        userName VARCHAR NOT null,
        password VARCHAR);

CREATE TABLE IF NOT EXISTS Messages (
        id SERIAL PRIMARY KEY NOT null,
        sender INTEGER,
        message VARCHAR,
        sending_time TIMESTAMP,
        CONSTRAINT fk_Messages_sender FOREIGN KEY (sender) REFERENCES Users(id));
