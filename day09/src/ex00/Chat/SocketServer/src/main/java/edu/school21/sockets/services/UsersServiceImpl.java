package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("UsersService")
public class UsersServiceImpl implements UsersService {

    private static final Long DEFAULT_ID = 1L;

    private UsersRepository repository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UsersServiceImpl(UsersRepository repository,
                            BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public boolean signUp(String name, String password) {
        if (repository.findByName(name).isPresent()) {
            return false;
        }
        repository.save(new User(DEFAULT_ID, name, encoder.encode(password)));
        return true;
    }
}
