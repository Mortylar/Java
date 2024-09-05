

--Users--

INSERT INTO Users(Login, Password) VALUES ('alelyka', '******');
INSERT INTO Users(Login, Password) VALUES ('vladiput', '******');
INSERT INTO Users(Login, Password) VALUES ('valstain', '******');
INSERT INTO Users(Login, Password) VALUES ('emmacro', '******');
INSERT INTO Users(Login, Password) VALUES ('xijin', '******');
INSERT INTO Users(Login, Password) VALUES ('joba', '*');

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

INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'Bathroom' AS Chatroom
    FROM Users
    WHERE Login = 'emmacro');


INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'Hospital' AS Chatroom
    FROM Users
    WHERE Login = 'joba');


INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'Kitchen' AS Chatroom
    FROM Users
    WHERE Login = 'xijin');



--UserChatroom
INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users
     WHERE Login IN ('alelyka', 'vladiput', 'xijin'))
    CROSS JOIN
    (SELECT Id AS ChatroomId
     FROM Chatroom
     WHERE name = 'BRICS'));


INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users
     WHERE Login NOT IN ('alelyka', 'vladiput', 'xijin'))
    CROSS JOIN
    (SELECT Id AS ChatroomId
     FROM Chatroom
     WHERE name = 'NATO'));


INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users)
    CROSS JOIN
    (SELECT Id AS ChatroomId
     FROM Chatroom
     WHERE name = 'Random'));


INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT Id AS ChatroomId, OwnerId AS UserId
FROM Chatroom
EXCEPT(
    SELECT ChatroomId, UserId
    FROM UserChatroom); 









