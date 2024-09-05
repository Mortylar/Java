package edu.school21.chat.models;

import java.util.Calendar;

class Message implements ITable {

    private final int messageId_;
    private User author_;
    private Chatroom room_;
    private String text_;
    private Calendar dateTime_;

    @Override
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof Message)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        return messageId_ == (Message)other.messageId_;
    }

    @Override
    public int hashCode() {
        return ~MessageId_;
    }

    @Override
    public String toString() {
        return String.format(
            "Message: id = %d, autor = { %s }, chatroom = { %s }, time = %s, text_ = %s.",
            messageId_, author_.toString(), room_.toString(),
            dateTime_.toString(), text_);
    }
}
