package edu.school21.chat.app;

import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;
import java.util.Scanner;
import javax.sql.DataSource;

public class DataBaseService {

    private static final long INCORRECT_INPUT = -1;
    private static final long MIN_ID = 1;

    private DataSource dataSource_;

    public DataBaseService(DataSource source) { dataSource_ = source; }

    public void findMessage() {
        printInvitation();
        long messageId = INCORRECT_INPUT;
        while (messageId == INCORRECT_INPUT) {
            messageId = readMessageId();
        }
        final long id = messageId;
        getMessage(messageId).ifPresentOrElse(
            message
            -> System.out.printf("\n%s\n", message.toString()),
            () -> System.out.printf("\nMessage with id = %s not found.\n", id));
    }

    private void printInvitation() {
        System.out.printf("Enter a message ID\n");
    }

    private long readMessageId() {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNext()) {
            System.exit(0);
        }
        if (!scanner.hasNextLong()) {
            System.out.printf("Incorrect input - expected integer number.\n");
            return INCORRECT_INPUT;
        }

        long messageId = scanner.nextLong();

        if (messageId < MIN_ID) {
            System.out.printf("Incorrect input - number not in id range.\n");
            return INCORRECT_INPUT;
        }
        return messageId;
    }

    Optional<Message> getMessage(long id) {
        MessagesRepositoryJdbcImpl objectOfClassWithIndistinctName =
            new MessagesRepositoryJdbcImpl(dataSource_);
        return objectOfClassWithIndistinctName.findById(id);
    }

    public void saveMessage() throws NotSavedSubEntityException, SQLException {
        User user = new User(2L);
        Chatroom room = new Chatroom(5L);
        String text = "";
        MessagesRepositoryJdbcImpl x =
            new MessagesRepositoryJdbcImpl(dataSource_);
        x.save(new Message(0, user, room, text, Calendar.getInstance()));
    }

    public void updateMessage() throws SQLException {
        User user = new User(2L);
        Chatroom room = new Chatroom(5L);
        String text = "There is no one here";
        MessagesRepositoryJdbcImpl x =
            new MessagesRepositoryJdbcImpl(dataSource_);
        x.update(new Message(6L, user, room, text, null));
    }
}
