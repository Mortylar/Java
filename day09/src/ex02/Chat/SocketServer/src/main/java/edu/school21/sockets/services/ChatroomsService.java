package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import java.util.List;

public interface ChatroomsService {

    public boolean load(String name);

    public List<Chatroom> findAll();
}
