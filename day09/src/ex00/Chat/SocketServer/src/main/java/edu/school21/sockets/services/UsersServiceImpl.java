package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import java.util.Optional;

public class UsersServiceImpl implements UsersService {

    private static final Long DEFAULT_ID = 1L;

    private UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean signUp(String name, String password) {
        if (repository.findByName(name).isPresent()) {
            return false;
        }
        repository.save(new User(DEFAULT_ID, name, password));
        return true;
    }
}
