package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import java.util.Optional;

public interface UsersService {

    public boolean signUp(String userName, String password);
    public Optional<User> signIn(String userName, String password);
}
