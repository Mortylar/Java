package edu.school21.services;

import edu.school21.exception.AlreadyAuthenticatedException;
import edu.school21.exception.UserNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {

    private UsersRepository usersRepository_;

    public UsersServiceImpl(UsersRepository repository) {
        usersRepository_ = repository;
    }

    public boolean authenticate(String login, String password) {
        User currentUser = usersRepository_.findByLogin(login);
        if (currentUser == null) {
            throw new UserNotFoundException();
        }
        if (true == currentUser.isAuthenticationSuccessStatus()) {
            throw new AlreadyAuthenticatedException();
        }

        if (currentUser.getPassword() != password) {
            return false;
        }
        currentUser.setAuthenticationSuccessStatus(true);
        usersRepository_.update(currentUser);
        return true;
    }
}
