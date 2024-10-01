package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SimplePropertySqlParameterSource;

public class UsersRepositoryImpl implements UsersRepository {

    private final int ID_INDEX = 1;
    private final int NAME_INDEX = 2;
    private final int PASSWORD_INDEX = 3;

    private final RowMapper<User> mapRow = (rs, rowNum) -> {
        User user = new User(rs.getLong(ID_INDEX), rs.getString(NAME_INDEX),
                             rs.getString(PASSWORD_INDEX));
        return user;
    };

    private NamedParameterJdbcTemplate template;

    public UsersRepositoryImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = String.format("SELECT * FROM Users WHERE id = %d;", id);
        User user = template.queryForObject(
            query, new EmptySqlParameterSource(), mapRow);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        String query =
            String.format("SELECT * FROM Users WHERE userName = '%s';", name);
        User user = template.queryForObject(
            query, new EmptySqlParameterSource(), mapRow);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        String query = String.format("SELECT * FROM Users;");
        return this.template.query(query, mapRow);
    }

    @Override
    public void save(User user) {
        this.template.update(
            String.format(
                "INSERT INTO Users(userName, password) VALUES('%s', '%s');",
                user.getUserName(), user.getPassword()),
            new EmptySqlParameterSource());
    }

    @Override
    public void update(User user) {
        String query = String.format(
            "UPDATE Users SET userName = '%s', password = '%s' WHERE id = %d;",
            user.getUserName(), user.getPassword(), user.getId());
        this.template.update(query, new EmptySqlParameterSource());
    }

    @Override
    public void delete(Long id) {
        String query = String.format("DELETE FROM Users WHERE id = %d;", id);
        this.template.update(query, new EmptySqlParameterSource());
    }
}
