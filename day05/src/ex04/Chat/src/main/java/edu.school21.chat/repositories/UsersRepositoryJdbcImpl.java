package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private static final int USER_ID_INDEX = 1;
    private static final int LOGIN_INDEX = 2;
    private static final int PASSWORD_INDEX = 3;
    private static final int CREATED_ROOM_ID_INDEX = 4;
    private static final int CREATED_ROOM_NAME_INDEX = 5;
    private static final int AVAILABLE_ROOM_ID_INDEX = 6;
    private static final int AVAILABLE_ROOM_NAME_INDEX = 7;
    private static final int AVAILABLE_ROOM_OWNER_INDEX = 8;

    private DataSource dataSource_;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        dataSource_ = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        ResultSet lines = getResultLines(generateQuery(size, page * size));

        return extractUsers(lines);
    }

    private List<User> extractUsers(ResultSet lines) {
        List<User> userList = new ArrayList<User>();
        try {
            if (lines.next()) {
                while (!lines.isAfterLast()) {
                    userList.add(extractUser(lines));
                }
            }
        } catch (SQLException e) {
            System.err.printf("\n%s\n", e.getMessage());
        }

        return userList;
    }

    private User extractUser(ResultSet lines) throws SQLException {
        long userId = lines.getLong(USER_ID_INDEX);
        String login = lines.getString(LOGIN_INDEX);
        String password = lines.getString(PASSWORD_INDEX);

        User user = new User(userId, login, password, new ArrayList<Chatroom>(),
                             new ArrayList<Chatroom>());

        while (user.getId() == userId) {
            user.addCreatedRoom(extractCreatedRoom(lines, user));
            user.addAvailableRoom(extractAvailableRoom(lines));
            if (!lines.next()) {
                return user;
            }
            userId = lines.getLong(USER_ID_INDEX);
        }
        return user;
    }

    private Chatroom extractCreatedRoom(ResultSet line, User creator)
        throws SQLException {
        long id = line.getLong(CREATED_ROOM_ID_INDEX);
        String name = line.getString(CREATED_ROOM_NAME_INDEX);
        return new Chatroom(id, name, creator, new ArrayList<Message>());
    }

    private Chatroom extractAvailableRoom(ResultSet line) throws SQLException {
        long id = line.getLong(AVAILABLE_ROOM_ID_INDEX);
        String name = line.getString(AVAILABLE_ROOM_NAME_INDEX);
        long ownerId = line.getLong(AVAILABLE_ROOM_OWNER_INDEX);
        return new Chatroom(id, name, new User(ownerId),
                            new ArrayList<Message>());
    }

    private ResultSet getResultLines(String query) {
        try {
            Connection connection = dataSource_.getConnection();
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.printf("\n%s\n", e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    private String generateQuery(int count, int offset) {
        String usersCutting =
            String.format(("WITH users_cutting AS (SELECT * FROM Users\n"
                           + "ORDER BY id\nLIMIT %d OFFSET %d),\n"),
                          count, offset);

        String createdRooms =
            "created_rooms AS (SELECT uc.id AS userId, uc.login, uc.password,\n"
            +
            "room.id AS RoomId, room.name AS RoomName\nFROM users_cutting AS uc\n"
            + "JOIN Chatroom AS room ON uc.id = room.ownerId),\n";

        String availableRooms =
            "available_rooms AS (SELECT uc.id AS UserId, uc.login, uc.password,\n"
            +
            "room.Id AS RoomId, room.name AS RoomName, room.OwnerId As RoomOwner\n"
            + "FROM users_cutting AS uc\n"
            + "JOIN UserChatroom As u_room ON uc.id = u_room.userId\n"
            + "JOIN Chatroom As room ON u_room.ChatroomId = room.id),\n";

        String userData =
            "user_data AS (SELECT cr.UserId As UserId, cr.login As login, cr.password AS password,\n"
            + "cr.RoomId AS cr_roomId, cr.RoomName AS cr_roomName,\n"
            +
            "av.RoomId AS av_roomId, av.RoomName AS av_roomName, av.RoomOwner AS av_roomOwner\n"
            +
            "FROM created_rooms AS cr JOIN available_rooms AS av ON cr.UserId = av.UserId)\n";

        return (usersCutting + createdRooms + availableRooms + userData +
                "SELECT * FROM user_data ORDER BY UserId;");
    }
}
