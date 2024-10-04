package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {

    public Optional<User> findByName(String name);
}
