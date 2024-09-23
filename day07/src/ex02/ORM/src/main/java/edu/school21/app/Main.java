package edu.school21.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.manager.OrmManager;
import edu.school21.models.User;

public class Main {

    public static void main(String[] args) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/mortylar");
        config.setUsername("mortylar");

        OrmManager manager = new OrmManager.OrmManagerBuilder()
                                 .setDataSource(new HikariDataSource(config))
                                 .addAnnotationClass(User.class)
                                 .addAnnotationClass(Main.class)
                                 .setRemovingTables()
                                 .build();
    }
}
