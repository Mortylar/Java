package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.models.Reader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws NotSavedSubEntityException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/mortylar");
        config.setUsername("mortylar");

        HikariDataSource ds = new HikariDataSource(config);
        try {
            Connection conn = ds.getConnection();
            Statement statement = conn.createStatement();

            createDataBase(statement);
            DataBaseService service = new DataBaseService(ds);
            service.findMessage();
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void createDataBase(Statement statement)
        throws IOException, SQLException {
        Reader reader = new Reader();
        statement.executeUpdate(reader.read("/schema.sql"));
        statement.executeUpdate(reader.read("/data.sql"));
    }
}
