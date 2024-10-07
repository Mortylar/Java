package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

public class ChatroomsRepositoryImpl implements ChatroomsRepository {

    private final int ID_INDEX = 1;
    private final int NAME_INDEX = 2;

    private final RowMapper<Chatroom> mapRow = (rs, rowNum) -> {
        Chatroom room =
            new Chatroom(rs.getLong(ID_INDEX), rs.getString(NAME_INDEX),
                         new ArrayList<Message>());
        return room;
    };

    private JdbcTemplate template;

    @Autowired
    public ChatroomsRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        String query = "SELECT * FROM Chatrooms WHERE id = ?;";
        try {
            Chatroom chatroom = this.template.queryForObject(query, mapRow, id);
            return Optional.ofNullable(chatroom);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Chatroom> findByName(String name) {
        String query = "SELECT * FROM Chatrooms WHERE name = ?;";
        try {
            Chatroom chatroom =
                this.template.queryForObject(query, mapRow, name);
            return Optional.ofNullable(chatroom);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Chatroom> findAll() {
        String query = String.format("SELECT * FROM Chatrooms;");
        return this.template.query(query, mapRow);
    }

    @Override
    public void save(Chatroom room) {
        String query = "INSERT INTO Chatrooms(name) VALUES(?);";
        this.template.update(room.getName());
    }

    @Override
    public void update(Chatroom room) {
        String query = "UPDATE Chatrooms SET name = ? WHERE id = ?;";
        this.template.update(room.getName(), room.getId());
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM Chatrooms WHERE id = ?;";
        this.template.update(query, id);
    }
}
