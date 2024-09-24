package edu.school21.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.exceptions.CreateTableException;
import edu.school21.exceptions.ObjectNotFoundException;
import edu.school21.exceptions.ObjectNotSavedException;
import edu.school21.manager.OrmManager;
import edu.school21.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class OrmManagerTest {

    private final User correctUser = new User(1L, "Vladimir", "Putin", 123);
    private final User updatedCorrectUser =
        new User(1L, "Vladimir", "Putin", 18);
    private final User forgetfulUser = new User(2L, "Joe", null, null);
    private final User incorrectIdUser = new User(-1L, null, null, null);
    private final Long incorrectUserId = -13L;

    private static OrmManager manager;

    @BeforeAll
    public static void init() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mortylar");
        config.setUsername("mortylar");

        manager = new OrmManager.OrmManagerBuilder()
                      .setDataSource(new HikariDataSource(config))
                      .addAnnotationClass(User.class)
                      .addAnnotationClass(OrmManagerTest.class)
                      .setRemovingTables()
                      .build();
    }

    @Test
    public void saveCorrectTest() {
        assertDoesNotThrow(() -> {
            manager.save(correctUser);
            User findedUser = manager.findById(correctUser.getId(), User.class);
            assertTrue(correctUser.equals(findedUser));
        });
    }

    @Test
    public void saveForgetfulTest() {
        assertDoesNotThrow(() -> {
            manager.save(forgetfulUser);
            User findedUser =
                manager.findById(forgetfulUser.getId(), User.class);
            assertTrue(forgetfulUser.equals(findedUser));
        });
    }

    @Test
    public void saveNullTest() {
        assertThrows(ObjectNotSavedException.class, () -> manager.save(null));
    }

    @Test
    public void saveInvalidTypeTest() {
        assertThrows(ObjectNotSavedException.class, () -> manager.save(1));
    }

    /*    @Test
        public void saveInfectiveUserTest() {
            assertThrows(ObjectNotSavedException.class,
                    () -> manager.save(infectiveUser));
        }*/

    @Test
    public void updateCorrectTest() {
        assertDoesNotThrow(() -> { manager.update(updatedCorrectUser); });
    }

    @Test
    public void updateIncorrectTypeTest() {
        assertThrows(ObjectNotSavedException.class, () -> manager.update(1));
    }

    @Test
    public void updateNullTest() {
        assertThrows(ObjectNotSavedException.class, () -> manager.update(null));
    }

    @Test
    public void findByIncorrectIdUserTest() {
        assertThrows(ObjectNotFoundException.class,
                     () -> manager.findById(incorrectUserId, User.class));
    }

    @Test
    public void findByIdInvalidTypeTest() {
        assertThrows(ObjectNotFoundException.class,
                     () -> manager.findById(correctUser.getId(), String.class));
    }
}
