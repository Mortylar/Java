package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import java.util.List;
import java.util.Optional;

public interface ChatroomsRepository extends CrudRepository<Chatroom> {

    public Optional<Chatroom> findByName(String name);
}
