package school21.spring.service.repositories;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SimplePropertySqlParameterSource;
import school21.spring.service.models.User;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final int ID_INDEX = 1;
    private final int EMAIL_INDEX = 2;

    private final RowMapper<User> mapRow = (rs, rowNum) -> {
        User user = new User(rs.getLong(ID_INDEX), rs.getString(EMAIL_INDEX));
        return user;
    };

    private NamedParameterJdbcTemplate template;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
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
    public Optional<User> findByEmail(String email) {
        String query =
            String.format("SELECT * FROM Users WHERE email = '%s';", email);
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
            "INSERT INTO Users(email) VALUES(?);",
            new SimplePropertySqlParameterSource(user.getEmail()));
    }

    @Override
    public void update(User user) {
        String query =
            String.format("UPDATE Users SET email = '%s' WHERE id = %d;",
                          user.getEmail(), user.getId());
        this.template.update(query, new EmptySqlParameterSource());
    }

    @Override
    public void delete(Long id) {
        String query = String.format("DELETE FROM Users WHERE id = %d;", id);
        this.template.update(query, new EmptySqlParameterSource());
    }
}
