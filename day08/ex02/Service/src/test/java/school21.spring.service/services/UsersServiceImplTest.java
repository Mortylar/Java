package school21.spring.service.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.services.UsersService;

public class UsersServiceImplTest {

    private static final String TEST_EMAIL = "aboba@student.21-school.ru";
    private static final int PASSWORD_MIN_LENGTH = 1;

    AbstractApplicationContext context;
    UsersService service;

    @BeforeEach
    public void createContext() {
        this.context =
            new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        service = (UsersService)(this.context.getBean("UsersService"));
    }

    @Test
    public void signUpTest() {
        String password = service.signUp(TEST_EMAIL);
        assertNotNull(password);
        assertTrue(password.length() >= PASSWORD_MIN_LENGTH);

        assertThrows(RuntimeException.class, () -> service.signUp(TEST_EMAIL));
    }
}
