package edu.school21.chat.models;

import java.util.List;

public class Chatroom implements ITable {

    private final long chatroomId_;
    private String name_;
    private User owner_;
    private List<Message> messages_;

    public Chatroom(long id) { chatroomId_ = id; };

    public Chatroom(long id, String name, User owner, List<Message> messages) {
        chatroomId_ = id;
        name_ = name;
        owner_ = owner;
        messages_ = messages;
    }

    @Override
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof Chatroom)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        return chatroomId_ == ((Chatroom)other).chatroomId_;
    }

    @Override
    public int hashCode() {
        return ~(int)chatroomId_;
    }

    @Override
    public String toString() {
        String result =
            String.format("Chatroom %s: id = %d, owner = { %s }, messages = { ",
                          (name_ == null) ? "null" : name_, chatroomId_,
                          (owner_ == null) ? "null" : owner_.toString());
        if (messages_ == null) {
            result += "null";
        } else {
            for (int i = 0; i < messages_.size(); ++i) {
                result += String.format("%s; ", messages_.get(i).toString());
            }
        }
        return result += "}";
    }

    public void setName(String name) { name_ = name; }

    public void setOwner(User owner) { owner_ = owner; }

    public void setMessages(List<Message> messages) { messages_ = messages; }

    public long setId() { return chatroomId_; }

    public String setName() { return name_; }

    public User setOwner() { return owner_; }

    public List<Message> setMessages() { return messages_; }
}
