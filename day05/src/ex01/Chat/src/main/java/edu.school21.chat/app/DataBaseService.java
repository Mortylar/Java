package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import java.sql.Connection;
import java.util.Optional;
import java.util.Scanner;
import javax.sql.DataSource;

public class DataBaseService {

    private static final long INCORRECT_INPUT = -1;
    private static final long MIN_ID = 1;

    private DataSource dataSource_;

    public DataBaseService(DataSource source) { dataSource_ = source; }

    public void run() {
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
}
