package edu.school21.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import edu.school21.exception.AlreadyAuthenticatedException;
import edu.school21.exception.UserNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.services.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class UsersServiceImplTest {

    private final User TEST_USER_NOT_AUTH =
        new User(1L, "Login", "Password", false);
    private final User TEST_USER_AUTH = new User(1L, "Login", "Password", true);
    private final String INCORRECT_PASSWORD = "qwerty123";

    private UsersRepository usersRepository_;

    @BeforeEach
    public void init() {
        usersRepository_ = Mockito.mock(UsersRepository.class);
    }

    @Test
    public void userNotFoundExceptionTest() {

        Mockito.when(usersRepository_.findByLogin(null)).thenReturn(null);

        UsersServiceImpl service = new UsersServiceImpl(usersRepository_);
        assertThrows(UserNotFoundException.class,
                     () -> service.authenticate(null, null));
    }

    @Test
    public void alreadyAuthenticatedExceptionTest() {

        Mockito.when(usersRepository_.findByLogin(TEST_USER_AUTH.getLogin()))
            .thenReturn(TEST_USER_AUTH);

        UsersServiceImpl service = new UsersServiceImpl(usersRepository_);
        assertThrows(AlreadyAuthenticatedException.class,
                     ()
                         -> service.authenticate(TEST_USER_AUTH.getLogin(),
                                                 TEST_USER_AUTH.getPassword()));
    }

    @Test
    public void incorrectPasswordTest() {

        Mockito
            .when(usersRepository_.findByLogin(TEST_USER_NOT_AUTH.getLogin()))
            .thenReturn(TEST_USER_NOT_AUTH);

        UsersServiceImpl service = new UsersServiceImpl(usersRepository_);
        assertEquals(false, service.authenticate(TEST_USER_AUTH.getLogin(),
                                                 INCORRECT_PASSWORD));
    }

    @Test
    public void successAuthenticationTest() {

        Mockito
            .when(usersRepository_.findByLogin(TEST_USER_NOT_AUTH.getLogin()))
            .thenReturn(TEST_USER_NOT_AUTH);

        UsersServiceImpl service = new UsersServiceImpl(usersRepository_);
        assertEquals(true,
                     service.authenticate(TEST_USER_NOT_AUTH.getLogin(),
                                          TEST_USER_NOT_AUTH.getPassword()));
    }
}
