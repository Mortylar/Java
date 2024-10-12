package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.ChatroomsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ChatroomsService")
public class ChatroomsServiceImpl implements ChatroomsService {

    private static final Long DEFAULT_ID = 1L;

    private ChatroomsRepository chatroomRepository;

    @Autowired
    public ChatroomsServiceImpl(ChatroomsRepository cRepository) {
        this.chatroomRepository = cRepository;
    }

    @Override
    public boolean load(String name) {
        if (chatroomRepository.findByName(name).isPresent()) {
            return false;
        }
        chatroomRepository.save(
            new Chatroom(DEFAULT_ID, name, new ArrayList<Message>()));
        return true;
    }

    @Override
    public List<Chatroom> findAll() {
        return chatroomRepository.findAll();
    }
}
