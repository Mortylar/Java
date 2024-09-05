package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Main {

    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/mortylar");
        config.setUsername("mortylar");

        HikariDataSource ds = new HikariDataSource(config);
        System.out.printf("\nSDGEHGRHJRETRW\n");
    }
}
