package edu.school21.chat.models;

import java.util.List;

public class User implements ITable {

    private final long userId_;
    private String login_;
    private String password_;
    private List<Chatroom> createdRooms_;
    private List<Chatroom> availableRooms_;

    public User(long id) { userId_ = id; };

    public User(long id, String login, String password,
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
        return ~(int)userId_;
    }

    @Override
    public String toString() {
        return String.format("User: id = %d, login = %s", userId_, login_);
    }

    public void setLogin(String login) { login_ = login; }

    public void setPassword(String password) { password_ = password; }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        createdRooms_ = createdRooms;
    }

    public void setAvailableRooms(List<Chatroom> availableRooms) {
        availableRooms_ = availableRooms;
    }

    public void addCreatedRoom(Chatroom room) {
        if (!createdRooms_.contains(room)) {
            createdRooms_.add(room);
        }
    }

    public void addAvailableRoom(Chatroom room) {
        if (!availableRooms_.contains(room)) {
            availableRooms_.add(room);
        }
    }

    public long getId() { return userId_; }

    public String getLogin() { return login_; }

    public String getPassword() { return password_; }

    public List<Chatroom> getCreatedRooms() { return createdRooms_; }

    public List<Chatroom> getAvailableRooms() { return availableRooms_; }
}
