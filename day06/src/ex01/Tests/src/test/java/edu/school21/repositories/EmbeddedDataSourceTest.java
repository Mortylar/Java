package edu.school21.repositories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class EmbeddedDataSourceTest {

    Connection connection_;
    EmbeddedDatabase db_;

    @BeforeEach
    public void init() {
        try {
            db_ = new EmbeddedDatabaseBuilder()
                      .generateUniqueName(true)
                      .setType(EmbeddedDatabaseType.HSQL)
                      .setScriptEncoding("UTF-8")
                      .ignoreFailedDrops(true)
                      .addScript("schema.sql")
                      .addScript("data.sql")
                      .build();
            connection_ = db_.getConnection();
            assertNotNull(connection_);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void printTest() {
        assertDoesNotThrow(() -> {
            Statement statement = connection_.createStatement();
            ResultSet r = statement.executeQuery("SELECT * FROM Product");
            System.out.printf("\nProduct table:\n");
            while (!r.isLast()) {
                r.next();
                System.out.printf("\n\tid = %d, name = %s, price = %f\n",
                                  r.getLong(1), r.getString(2), r.getDouble(3));
            }
            System.out.println();
        });
    }

    @AfterEach
    private void closeConnection() {
        assertDoesNotThrow(() -> { connection_.close(); });
    }
}
