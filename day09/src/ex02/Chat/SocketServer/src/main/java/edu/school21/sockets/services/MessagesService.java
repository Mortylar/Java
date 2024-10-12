package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import java.util.List;

public interface MessagesService {

    public boolean load(String userName, String roomName, String messageText);

    public List<Message> findNLastInRoom(String room, int count);
}
