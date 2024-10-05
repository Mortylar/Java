package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import java.sql.Timestamp;
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
    private final int TEXT_INDEX = 3;
    private final int TIME_INDEX = 4;
    private final int SENDER_NAME_INDEX = 5;
    private final int SENDER_PASSWORD_INDEX = 6;

    private final RowMapper<Message> mapRow = (rs, rowNum) -> {
        User user = new User(rs.getLong(SENDER_ID_INDEX),
                             rs.getString(SENDER_NAME_INDEX),
                             rs.getString(SENDER_PASSWORD_INDEX));
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(rs.getTimestamp(TIME_INDEX).getTime());
        Message message = new Message(rs.getLong(ID_INDEX), user,
                                      rs.getString(TEXT_INDEX), time);
        return message;
    };

    private JdbcTemplate template;

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT * FROM messages JOIN users "
                       + "ON users.id = sender_id "
                       + "AND messages.id = ?;";
        try {
            Message message = this.template.queryForObject(query, mapRow, id);
            return Optional.ofNullable(message);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        String query = "SELECT * FROM messages JOIN users "
                       + "ON users.id = sender_id;";
        return this.template.query(query, mapRow);
    }

    @Override
    public void save(Message message) {
        String query = "INSERT INTO messages(sender_id, message, sending_time) "
                       + "VALUES(?, ?, ?);";
        this.template.update(
            query, message.getSender().getId(), message.getText(),
            new Timestamp(message.getTime().getTimeInMillis()));
    }

    @Override
    public void update(Message message) {
        String query = "UPDATE messages SET sender_id = ?, message = ?, "
                       + "sending_time = ? WHERE id = ?;";
        this.template.update(query, message.getSender().getId(),
                             message.getText(),
                             new Timestamp(message.getTime().getTimeInMillis()),
                             message.getId());
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM messages WHERE id = ?;";
        this.template.update(query, id);
    }
}
