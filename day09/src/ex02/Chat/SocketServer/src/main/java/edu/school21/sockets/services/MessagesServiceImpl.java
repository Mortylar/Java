package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatroomsRepository;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import java.util.Calendar;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MessageService")
public class MessagesServiceImpl implements MessagesService {

    private static final Long DEFAULT_ID = 1L;

    private ChatroomsRepository chatroomsRepository;
    private MessagesRepository messagesRepository;
    private UsersRepository usersRepository;

    @Autowired
    public MessagesServiceImpl(MessagesRepository mRepository,
                               UsersRepository uRepository,
                               ChatroomsRepository cRepository) {
        this.messagesRepository = mRepository;
        this.usersRepository = uRepository;
        this.chatroomsRepository = cRepository;
    }

    @Override
    public boolean load(String userName, String roomName, String messageText) {
        Optional<User> user = usersRepository.findByName(userName);
        Optional<Chatroom> room = chatroomsRepository.findByName(roomName);
        if (user.isPresent() && room.isPresent()) {
            messagesRepository.save(new Message(DEFAULT_ID, user.get(),
                                                room.get(), messageText,
                                                Calendar.getInstance()));
            return true;
        }
        return false;
    }
}
