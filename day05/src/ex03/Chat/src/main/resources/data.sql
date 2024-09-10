

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
    WHERE Login = 'vladiput') AS line;


INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'NATO' AS Chatroom
    FROM Users
    WHERE Login = 'joba') AS line;


INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'Random' AS Chatroom
    FROM Users
    WHERE Login = 'alelyka') AS line;

INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'Bathroom' AS Chatroom
    FROM Users
    WHERE Login = 'emmacro') AS line;


INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'Hospital' AS Chatroom
    FROM Users
    WHERE Login = 'joba') AS line;


INSERT INTO Chatroom(Name, OwnerId)
SELECT Chatroom, UserId
FROM (
    SELECT Id AS UserId, 'Kitchen' AS Chatroom
    FROM Users
    WHERE Login = 'xijin') AS line;



--UserChatroom
INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users
     WHERE Login IN ('alelyka', 'vladiput', 'xijin')) AS first
    CROSS JOIN
    (SELECT Id AS ChatroomId
     FROM Chatroom
     WHERE name = 'BRICS') AS second);


INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users
     WHERE Login NOT IN ('alelyka', 'vladiput', 'xijin')) AS first
    CROSS JOIN
    (SELECT Id AS ChatroomId
     FROM Chatroom
     WHERE name = 'NATO') AS second);


INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT UserId, ChatroomId
FROM (
    (SELECT Id As UserId
     FROM Users) AS first
    CROSS JOIN
    (SELECT Id AS ChatroomId
     FROM Chatroom
     WHERE name = 'Random') AS second);


INSERT INTO UserChatroom(UserId, ChatroomId)
SELECT Id AS ChatroomId, OwnerId AS UserId
FROM Chatroom
EXCEPT(
    SELECT ChatroomId, UserId
    FROM UserChatroom); 

--Message

INSERT INTO Message(AuthorId, RoomId, Text, MessageTime)
VALUES(2, 4, 'Bonjour, Do you order a pizza with cheese holes?', CURRENT_TIMESTAMP);

INSERT INTO Message(AuthorId, RoomId, Text, MessageTime)
VALUES(4, 4, 'Non, I ordered a pizza with cheese without holes.', CURRENT_TIMESTAMP);

INSERT INTO Message(AuthorId, RoomId, Text, MessageTime)
VALUES(2, 4, 'I''m sorry, but cheese is gone, only holes remain.', CURRENT_TIMESTAMP);

INSERT INTO Message(AuthorId, RoomId, Text, MessageTime)
VALUES(4, 4, '<censored>***</censored>', CURRENT_TIMESTAMP);

INSERT INTO Message(AuthorId, RoomId, Text, MessageTime)
VALUES(6, 5, 'Hello! Is anybody here?', CURRENT_TIMESTAMP);






