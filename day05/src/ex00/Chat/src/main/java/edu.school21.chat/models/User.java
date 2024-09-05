package edu.school21.chat.models;

import java.util.List;

public class User implements ITable {

    private final int userId_;
    private String login_;
    private String password_;
    private List<Chatroom> createdRooms_;
    private List<Chatroom> availableRooms_;

    public User(int id, String login, String password,
                List<Chatroom> createdRooms, List<Chatroom> availableRooms) {
        userId_ = id;
        login_ = login;
        password_ = password;
        createdRooms_ = createdRooms;
        availableRooms_ = availableRooms;
    }

    @Override
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof User)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        return userId_ == ((User)other).userId_;
    }

    @Override
    public int hashCode() {
        return ~userId_;
    }

    @Override
    public String toString() {
        return String.format("User: id = %d, login = %s.", userId_, login_);
    }
}
