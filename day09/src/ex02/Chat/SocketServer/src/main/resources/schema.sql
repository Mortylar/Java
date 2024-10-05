DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Messages CASCADE;
DROP TABLE IF EXISTS Chatrooms CASCADE;

CREATE TABLE IF NOT EXISTS Users (
        id SERIAL PRIMARY KEY NOT null,
        userName VARCHAR NOT null,
        password VARCHAR);

CREATE TABLE IF NOT EXISTS Messages (
        id SERIAL PRIMARY KEY NOT null,
        sender_id INTEGER,
        chatroom_id INTEGER,
        message VARCHAR,
        sending_time TIMESTAMP,
        CONSTRAINT fk_Messages_sender FOREIGN KEY (sender_id) REFERENCES Users(id),
        CONSTRAINT fk_Messages_chatroom FOREIGN KEY (chatroom_id) REFERENCES Chatrooms(id));

CREATE TABLE IF NOT EXISTS Chatrooms (
        id SERIAL PRIMARY KEY NOT null,
        name VARCHAR);
