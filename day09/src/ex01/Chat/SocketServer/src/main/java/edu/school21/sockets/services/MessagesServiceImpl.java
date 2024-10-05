package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import java.util.Calendar;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MessageService")
public class MessagesServiceImpl implements MessagesService {

    private static final Long DEFAULT_ID = 1L;

    private MessagesRepository messagesRepository;
    private UsersRepository usersRepository;

    @Autowired
    public MessagesServiceImpl(MessagesRepository mRepository,
                               UsersRepository uRepository) {
        this.messagesRepository = mRepository;
        this.usersRepository = uRepository;
    }

    @Override
    public boolean load(String userName, String messageText) {
        Optional<User> user = usersRepository.findByName(userName);
        if (user.isPresent()) {
            messagesRepository.save(new Message(
                DEFAULT_ID, user.get(), messageText, Calendar.getInstance()));
            return true;
        }
        return false;
    }
}
