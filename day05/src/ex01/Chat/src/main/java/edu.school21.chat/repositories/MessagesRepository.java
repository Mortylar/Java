package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;
import java.util.Optional;

public interface MessagesRepository {

    public Optional<Message> findById(long id);

    public void save(Message message);
}
