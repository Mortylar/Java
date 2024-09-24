package edu.school21.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.manager.OrmManager;
import edu.school21.models.User;

@Generated
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

        User user = new User(1L, "Vladimir", "Putin", 1234);
        manager.save(user);
        user.setAge(null);
        manager.update(user);
        System.out.printf("\nnew user = %s\n",
                          manager.findById(1L, User.class));
        try {
            manager.findById(null, User.class);
        } catch (Exception e) {
            System.out.printf("Catch exception:\n\t%s", e.getMessage());
        }
        try {
            manager.findById(2L, User.class);
        } catch (Exception e) {
            System.out.printf("Catch exception:\n\t%s", e.getMessage());
        }
    }
}
