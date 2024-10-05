package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

public interface UsersService {

    public boolean signUp(String userName, String password);
    public boolean signIn(String userName, String password);
}
