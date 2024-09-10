package edu.school21.chat.models;

import java.util.Calendar;

public class Message implements ITable {

    private final long messageId_;
    private User author_;
    private Chatroom room_;
    private String text_;
    private Calendar dateTime_;

    public Message(long id) { messageId_ = id; };

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
        String formatString = "Message: id = %d,\nAuthor = { %s },\n";
        formatString += "Chatroom = {\n\t%s },\ntime = %s,\ntext = %s\n";
        return String.format(formatString, messageId_,
                             (author_ == null) ? "null" : author_.toString(),
                             (room_ == null) ? "null" : room_.toString(),
                             (dateTime_ == null) ? "null" : dateTime_.getTime(),
                             text_);
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
