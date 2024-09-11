package edu.school21.chat.repositories;

import edu.school21.chat.models.User;
import java.util.List;

public interface UsersRepository {

    public List<User> findAll(int page, int size);
}
