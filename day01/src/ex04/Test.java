
import static transaction.Transaction.createTransactionOrReturnNull;
import static user.UserIdsGenerator.getInstance;

import exception.TransactionNotFoundException;
import exception.UserNotFoundException;
import java.util.Iterator;
import java.util.UUID;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionType;
import user.User;
import user.UserArrayList;
import user.UserIdsGenerator;

public class Test {

    private static final int UNKNOWN_TEST_NUMBER = 10;
    private static final int START_UP_CAPITAL = 100;

    private UserArrayList userList_;
    private TransactionLinkedList trList_;

    {
        userList_ = new UserArrayList();
        trList_ = new TransactionLinkedList();
    }

    public Test() {}

    public void runTest() {
        createUserList();
        printUserList();

        createTransactionList();
        printTransactionList();

        printUserList();

        removeSomeTransactions();

        System.out.printf("\n<================>\n");

        trList_.print();
    }

    public void createUserList() {
        for (int i = 0; i < UNKNOWN_TEST_NUMBER + 1; ++i) {
            userList_.add(new User("Vladimir" + i, START_UP_CAPITAL));
        }
    }

    public void printUserList() {
        for (int i = 0; i < userList_.size(); ++i) {
            userList_.getAt(i).print();
        }
        System.out.printf(".\n");
    }

    public void createTransactionList() {
        for (int i = 0; i < UNKNOWN_TEST_NUMBER; ++i) {
            try {
                User user1 = userList_.get(i + 1);
                User user2 = userList_.getAt(UNKNOWN_TEST_NUMBER - i);
                int payment = 10;
                Transaction tr1 = createTransactionOrReturnNull(
                    UUID.randomUUID(), user1, user2, TransactionType.DEBIT,
                    payment);
                Transaction tr2 = createTransactionOrReturnNull(
                    UUID.randomUUID(), user2, user1, TransactionType.CREDIT,
                    -payment);
                if ((tr1 == null) || (tr2 == null))
                    System.out.printf("NULL\n");
                trList_.add(tr1);
                trList_.add(tr2);
                user1.updateBalance(payment);
                user2.updateBalance(-payment);
            } catch (UserNotFoundException e) {
                System.out.printf("User with id %d not found in user list\n",
                                  i);
            }
        }
    }

    public void printTransactionList() {
        int size = trList_.size();
        Transaction[] array = trList_.toArray();
        for (int i = 0; i < size; ++i) {
            array[i].print();
        }
        System.out.println(".");
    }

    public void removeSomeTransactions() {
        Transaction[] array = trList_.toArray();
        for (int i = 0; i < UNKNOWN_TEST_NUMBER; ++i) {
            try {
                System.out.println("Transaction ");
                array[i].print();
                System.out.println("was removed.");
                trList_.remove(array[i].getID());
            } catch (TransactionNotFoundException e) {
                System.out.print("Transaction not found\n");
            }
        }
        System.out.println(".");
    }
}
