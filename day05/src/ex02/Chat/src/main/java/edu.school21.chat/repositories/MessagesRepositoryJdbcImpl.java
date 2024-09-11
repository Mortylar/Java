package edu.school21.chat.repositories;

import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private static final int MESSAGE_ID_INDEX = 1;
    private static final int AUTHOR_ID_INDEX = 2;
    private static final int CHATROOM_ID_INDEX = 3;
    private static final int TEXT_INDEX = 4;
    private static final int DATETIME_INDEX = 5;

    private DataSource dataBase_;

    public MessagesRepositoryJdbcImpl(DataSource dataBase) {
        dataBase_ = dataBase;
    }

    @Override
    public Optional<Message> findById(long id) {
        Message message;
        try {
            ResultSet line = getResultLine(
                String.format("SELECT * FROM Message WHERE id = %d;", id));
            message = buildMessage(line);
        } catch (Exception e) {
            System.out.printf("\n%s\n", e.getMessage());
            message = null;
        }
        return Optional.ofNullable(message);
    }

    private ResultSet getResultLine(String query) throws SQLException {
        Connection connection = dataBase_.getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    private Message buildMessage(ResultSet line) throws SQLException {
        if (!line.next()) {
            return null;
        }
        return new Message(getMessageId(line), getAuthor(line),
                           getChatroom(line), getText(line), getDateTime(line));
    }

    private long getMessageId(ResultSet line) throws SQLException {
        return line.getLong(MESSAGE_ID_INDEX);
    }

    private User getAuthor(ResultSet line) throws SQLException {
        long userId = line.getLong(AUTHOR_ID_INDEX);
        return getUserById(userId);
    }

    private User getUserById(long id) throws SQLException {
        final int LOGIN_INDEX = 2;
        final int PASSWORD_INDEX = 3;

        Connection connection = dataBase_.getConnection();
        Statement statement = connection.createStatement();
        ResultSet userData = statement.executeQuery(
            String.format("SELECT * FROM Users WHERE id = %d;", id));

        if (!userData.next()) {
            return null;
        }

        return new User(id, userData.getString(LOGIN_INDEX),
                        userData.getString(PASSWORD_INDEX),
                        new ArrayList<Chatroom>(), new ArrayList<Chatroom>());
    }

    private Chatroom getChatroom(ResultSet line) throws SQLException {
        long chatroomId = line.getLong(CHATROOM_ID_INDEX);
        return getChatroomById(chatroomId);
    }

    private Chatroom getChatroomById(long id) throws SQLException {
        final int NAME_INDEX = 2;

        Connection connection = dataBase_.getConnection();
        Statement statement = connection.createStatement();
        ResultSet chatroomData = statement.executeQuery(
            String.format("SELECT * FROM Chatroom WHERE id = %d;", id));
        if (!chatroomData.next()) {
            return null;
        }

        return new Chatroom(id, chatroomData.getString(NAME_INDEX), null, null);
    }

    private String getText(ResultSet line) throws SQLException {
        return line.getString(TEXT_INDEX);
    }

    private Calendar getDateTime(ResultSet line) throws SQLException {
        Calendar dateTime = Calendar.getInstance();
        Timestamp timestamp = line.getTimestamp(DATETIME_INDEX);
        dateTime.setTimeInMillis(timestamp.getTime());
        return dateTime;
    }

    @Override
    public void save(Message message) throws NotSavedSubEntityException {
        checkMessage(message);
        try {
            Connection connection = dataBase_.getConnection();
            Statement statement = connection.createStatement();
            String query =
                "INSERT INTO Message(AuthorId, RoomId, Text, MessageTime)\n";
            query += String.format(
                "VALUES(%d, %d, '%s', '%s')", message.getAuthor().getId(),
                message.getChatroom().getId(), message.getText(),
                new Timestamp(message.getDateTime().getTimeInMillis()));
            try {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                System.err.printf("\n%s\n", e.getMessage());
                throw new NotSavedSubEntityException();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void checkMessage(Message message)
        throws NotSavedSubEntityException {
        User author = message.getAuthor();
        Chatroom room = message.getChatroom();
        if ((author == null) || (room == null)) {
            throw new NotSavedSubEntityException();
        }

        final int MIN_ID = 1;
        if ((author.getId() < MIN_ID) || (room.getId() < MIN_ID)) {
            throw new NotSavedSubEntityException();
        }
    }
}
