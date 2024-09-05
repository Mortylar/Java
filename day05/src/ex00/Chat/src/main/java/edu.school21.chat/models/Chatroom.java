package edu.school21.chat.models;

import java.util.List;

public class Chatroom implements ITable {

    private final int chatroomId_;
    private String name_;
    private User owner_;
    private List<Message> messages_;

    @Override
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof Chatroom)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        return chatroomId_ == (Chatroom)other.chatroomId_;
    }

    @Override
    public int hashCode() {
        return ~chatroomId_;
    }

    @Override
    public String toString() {
        String result =
            String.format("Chatroom %s: id = %d, owner = { %s }, messages = { ",
                          name_, chatroomId_, owner.toString());
        for (int i = 0; i < messages_.size(); ++i) {
            result += String.format("%s; ", messages_.get(i).toString());
        }
        return result += "}"
    }
}
