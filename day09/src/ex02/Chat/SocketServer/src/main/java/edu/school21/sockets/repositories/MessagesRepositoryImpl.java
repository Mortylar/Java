package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("MessagesRepository")
public class MessagesRepositoryImpl implements MessagesRepository {

    private final int ID_INDEX = 1;
    private final int SENDER_ID_INDEX = 2;
    private final int CHATROOM_ID_INDEX = 3;
    private final int TEXT_INDEX = 4;
    private final int TIME_INDEX = 5;

    private static final Long DEFAULT_ID = 1L;

    private final RowMapper<Message> mapRow = (rs, rowNum) -> {
        User user =
            new User(rs.getLong(SENDER_ID_INDEX), new String(), new String());
        Chatroom room = new Chatroom(rs.getLong(CHATROOM_ID_INDEX),
                                     new String(), new ArrayList<Message>());
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(rs.getTimestamp(TIME_INDEX).getTime());

        Message message = new Message(rs.getLong(ID_INDEX), user, room,
                                      rs.getString(TEXT_INDEX), time);
        return message;
    };

    private final RowMapper<Message> miniMapRow = (rs, rowNum) -> {
        User sender =
            new User(DEFAULT_ID, rs.getString("userName"), new String());
        Calendar time = Calendar.getInstance();
        Chatroom room = new Chatroom();
        time.setTimeInMillis(rs.getTimestamp("time").getTime());
        return new Message(DEFAULT_ID, sender, room, rs.getString("message"),
                           time);
    };

    private JdbcTemplate template;

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT * FROM Messages WHERE id = ?;";
        try {
            Message message = this.template.queryForObject(query, mapRow, id);
            return Optional.ofNullable(message);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        String query = "SELECT * FROM Messages;";
        return this.template.query(query, mapRow);
    }

    @Override
    public void save(Message message) {
        String query =
            "INSERT INTO Messages(sender_id, chatroom_id, message, sending_time) "
            + "VALUES(?, ?, ?, ?);";
        this.template.update(
            query, message.getSender().getId(), message.getRoom().getId(),
            message.getText(),
            new Timestamp(message.getTime().getTimeInMillis()));
    }

    @Override
    public void update(Message message) {
        String query =
            "UPDATE messages SET sender_id = ?, chatroom_id = ?, message = ?, "
            + "sending_time = ? WHERE id = ?;";
        this.template.update(query, message.getSender().getId(),
                             message.getRoom().getId(), message.getText(),
                             new Timestamp(message.getTime().getTimeInMillis()),
                             message.getId());
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM messages WHERE id = ?;";
        this.template.update(query, id);
    }

    @Override
    public List<Message> findNLastInRoom(String room, int count) {
        String query = "WITH mini_message AS (\n"
                       + "SELECT mc.sender_id, mc.message, mc.sending_time\n"
                       + "FROM (SELECT * FROM Messages\n"
                       + "      JOIN Chatrooms\n"
                       + "      ON Chatrooms.id = chatroom_id) AS mc\n"
                       + "WHERE mc.name = ?)\n"
                       + "\n"
                       + "SELECT Users.userName AS userName,\n"
                       + "       mini_message.message AS message,\n"
                       + "       mini_message.sending_time AS time\n"
                       + "FROM mini_message\n"
                       + "JOIN Users ON mini_message.sender_id = Users.id\n"
                       + "ORDER BY time LIMIT ?;\n";

        return this.template.query(query, miniMapRow, room, count);
    }
}
