package school21.spring.service.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

@Scope("singleton")
@Component("UsersRepository")
public class UsersRepositoryJdbcImpl implements UsersRepository {

    private DataSource ds;
    private Connection connection;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("HikariDataSource")
                                   DataSource dataSource) {
        this.ds = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        String query = String.format("SELECT * FROM Users WHERE id = %d;", id);
        try {
            ResultSet resultLine = getResultSet(query);
            if (!resultLine.next()) {
                return Optional.ofNullable(user);
            }
            user = extractUser(resultLine);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        closeConnection();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        String query =
            String.format("SELECT * FROM Users WHERE email = '%s';", email);
        try {
            ResultSet resultLine = getResultSet(query);
            if (!resultLine.next()) {
                return Optional.ofNullable(user);
            }
            user = extractUser(resultLine);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        closeConnection();
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<User>();
        String query = String.format("SELECT * FROM Users;");
        try {
            ResultSet resultTable = getResultSet(query);
            while (!resultTable.isLast()) {
                resultTable.next();
                userList.add(extractUser(resultTable));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        closeConnection();
        return userList;
    }

    private ResultSet getResultSet(String query) throws SQLException {
        return createConnection().executeQuery(query);
    }

    private Statement createConnection() {
        try {
            this.connection = ds.getConnection();
            return connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private User extractUser(ResultSet line) throws SQLException {
        final int idIndex = 1;
        final int emailIndex = 2;
        return new User(line.getLong(idIndex), line.getString(emailIndex));
    }

    @Override
    public void save(User user) {
        checkUser(user);
        String query = String.format("INSERT INTO Users(email) VALUES('%s')",
                                     user.getEmail());
        updateDataBase(query);
        closeConnection();
    }

    @Override
    public void update(User user) {
        checkUser(user);
        String query =
            String.format("Update Users SET email = '%s' WHERE id = %d;",
                          user.getEmail(), user.getId());
        updateDataBase(query);
        closeConnection();
    }

    @Override
    public void delete(Long id) {
        String query = String.format("DELETE FROM Users WHERE id = %d;", id);
        updateDataBase(query);
        closeConnection();
    }

    private void updateDataBase(String query) {
        try {
            createConnection().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void checkUser(User user) {
        if (null == user) {
            throw new RuntimeException("User is null.");
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
