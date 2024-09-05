DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Chatroom CASCADE;
DROP TABLE IF EXISTS UserChatroom CASCADE;
DROP TABLE IF EXISTS Message CASCADE;


CREATE TABLE IF NOT EXISTS Users (
                Id SERIAL PRIMARY KEY NOT null,
                Login VARCHAR NOT null,
                Password VARCHAR NOT null);


CREATE TABLE IF NOT EXISTS Chatroom (
                Id SERIAL PRIMARY KEY NOT null,
                Name VARCHAR,
                OwnerId INTEGER,
                CONSTRAINT fk_Chatroom_OwnerId FOREIGN KEY (OwnerId) REFERENCES Users(Id));


CREATE TABLE IF NOT EXISTS UserChatroom (
                UserId INTEGER,
                ChatroomId INTEGER,
                CONSTRAINT fk_UserChatroom_UserId FOREIGN KEY (UserId) REFERENCES Users(Id),
                CONSTRAINT fk_UserChatroom_ChatroomId FOREIGN KEY (ChatroomId) REFERENCES Chatroom(Id));


CREATE TABLE IF NOT EXISTS Message (
                Id SERIAL PRIMARY KEY NOT null,
                AutorId INTEGER,
                RoomId INTEGER,
                Text VARCHAR,
                MessageTime TIMESTAMP,
                CONSTRAINT fk_Message_AutorId FOREIGN KEY (AutorId) REFERENCES Users(Id),
                CONSTRAINT fk_Message_RoomId FOREIGN KEY (RoomId) REFERENCES Chatroom(Id));

