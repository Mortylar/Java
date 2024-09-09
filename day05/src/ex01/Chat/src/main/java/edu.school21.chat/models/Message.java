package edu.school21.chat.models;

import java.util.Calendar;

public class Message implements ITable {

    private final long messageId_;
    private User author_;
    private Chatroom room_;
    private String text_;
    private Calendar dateTime_;

    public Message(long id){
        messageId_ = id;
    };

    public Message(long id, User author, Chatroom room, String text,
                   Calendar dateTime) {
        messageId_ = id;
        author_ = author;
        room_ = room;
        text_ = text;
        dateTime_ = dateTime;
    }

    @Override
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof Message)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        return messageId_ == ((Message)other).messageId_;
    }

    @Override
    public int hashCode() {
        return ~(int)messageId_;
    }

    @Override
    public String toString() {
        return String.format(
            "Message: id = %d, autor = { %s }, chatroom = { %s }, time = %s, text_ = %s.",
            messageId_, author_.toString(), room_.toString(),
            dateTime_.toString(), text_);
    }

    public void setAuthor(User user) { author_ = user; }

    public void setChatroom(Chatroom room) { room_ = room; }

    public void setText(String text) { text_ = text; }

    public void setDateTime(Calendar dateTime) { dateTime_ = dateTime; }

    public long getId() { return messageId_; }

    public User getAuthor() { return author_; }

    public Chatroom getChatroom() { return room_; }

    public String getText() { return text_; }

    public Calendar getDateTime() { return dateTime_; }
}
