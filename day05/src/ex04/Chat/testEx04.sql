WITH users_cutting AS (SELECT * FROM Users
ORDER BY id
LIMIT 3 OFFSET 3),
created_rooms AS (SELECT uc.id AS userId, uc.login, uc.password,
room.id AS RoomId, room.name AS RoomName
FROM users_cutting AS uc
JOIN Chatroom AS room ON uc.id = room.ownerId),
available_rooms AS (SELECT uc.id AS UserId, uc.login, uc.password,
room.Id AS RoomId, room.name AS RoomName, room.OwnerId As RoomOwner
FROM users_cutting AS uc
JOIN UserChatroom As u_room ON uc.id = u_room.userId
JOIN Chatroom As room ON u_room.ChatroomId = room.id),
user_data AS (SELECT cr.UserId As UserId, cr.login As login, cr.password AS password,
cr.RoomId AS cr_roomId, cr.RoomName AS cr_roomName,
av.RoomId AS av_roomId, av.RoomName AS av_roomName, av.RoomOwner AS av_roomOwner
FROM created_rooms AS cr JOIN available_rooms AS av ON cr.UserId = av.UserId)
SELECT * FROM user_data ORDER BY UserId;
