--allelyka
--vladiput
--joba
--emmacro
--valstain
--xijin


--Users--

INSERT INTO Users(Login, Password) VALUES ('alelyka', '******');
INSERT INTO Users(Login, Password) VALUES ('vladiput', '******');
INSERT INTO Users(Login, Password) VALUES ('valstain', '******');
INSERT INTO Users(Login, Password) VALUES ('emmacro', '******');
INSERT INTO Users(Login, Password) VALUES ('xijin', '******');
INSERT INTO Users(Login, Password) VALUES ('joba', '*');

--UserRoom--

INSERT INTO UserRoom(UserId, Room)
SELECT UserId, Room
FROM (
    SELECT Id AS UserId, 'garden' AS Room
    FROM Users
    WHERE Login = 'alelyka');

INSERT INTO UserRoom(UserId, Room)
SELECT UserId, Room
FROM (
    SELECT Id AS UserId, 'Kremlin' AS Room
    FROM Users
    WHERE Login = 'vladiput');

INSERT INTO UserRoom(UserId, Room)
SELECT UserId, Room
FROM (
    SELECT Id AS UserId, 'bathroom' AS Room
    FROM Users
    WHERE Login = 'emmacro');


INSERT INTO UserRoom(UserId, Room)
SELECT UserId, Room
FROM (
    SELECT Id AS UserId, 'hospital' AS Room
    FROM Users
    WHERE Login = 'joba');

INSERT INTO UserRoom(UserId, Room)
SELECT UserId, Room
FROM (
    SELECT Id AS UserId, 'underground' AS Room
    FROM Users
    WHERE Login = 'valstain');

INSERT INTO UserRoom(UserId, Room)
SELECT UserId, Room
FROM (
    SELECT Id AS UserId, 'kitchen' AS Room
    FROM Users
    WHERE Login = 'xijin');

--Chatroom--

INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'BRICS' AS Chatroom
    FROM Users
    WHERE Login = 'vladiput');


INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'NATO' AS Chatroom
    FROM Users
    WHERE Login = 'joba');


INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'Random' AS Chatroom
    FROM Users
    WHERE Login = 'alelyka');


--UserChatroom
INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users
     WHERE Login IN ('alelyka', 'vladiput', 'xijin'))
     AS UserId
    JOIN
    (SELECT Id
     FROM Chatroom
     WHERE name = 'BRICS')
     AS ChatroomId
    ON true);


INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users
     WHERE Login NOT IN ('alelyka', 'vladiput', 'xijin'))
     AS UserId
    JOIN
    (SELECT Id
     FROM Chatroom
     WHERE name = 'NATO')
     AS ChatroomId
    ON true);


INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users)
     AS UserId
    JOIN
    (SELECT Id
     FROM Chatroom
     WHERE name = 'Random')
     AS ChatroomId
    ON true);

