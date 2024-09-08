package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Reader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/mortylar");
        config.setUsername("mortylar");

        HikariDataSource ds = new HikariDataSource(config);

        Connection conn = ds.getConnection();
        Statement statement = conn.createStatement();

        createDataBase(statement);

        ResultSet res = statement.executeQuery("SELECT * FROM Users;");
        while (res.next()) {
            System.out.printf(" %d ", res.getInt(1));
            System.out.printf(" %s ", res.getString(2));
            System.out.printf(" %s \n", res.getString(3));
        }
    }

    public static void createDataBase(Statement statement)
        throws IOException, SQLException {
        Reader reader = new Reader();
        statement.executeUpdate(reader.read("/schema.sql"));
        statement.executeUpdate(reader.read("/data.sql"));
    }
}
