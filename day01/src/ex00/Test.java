
import static transaction.Transaction.createTransactionOrReturnNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import transaction.Transaction;
import transaction.TransactionType;
import user.User;

public class Test {

    private static final int UNKNOWN_TEST_NUMBER = 10;
    private static final int START_UP_CAPITAL = 100;

    private ArrayList<User> userList_;
    private ArrayList<Transaction> transactionList_;

    {
        userList_ = new ArrayList<User>();
        transactionList_ = new ArrayList<Transaction>();
    }

    public Test() {}

    public void runTest() {
        createUserList();
        printUserList();
        System.out.printf("\n");

        createTransactionList();
        printTransactionList();
        System.out.printf("\n");

        printUserList();
    }

    public void createUserList() {
        for (int i = 0; i < UNKNOWN_TEST_NUMBER; ++i) {
            userList_.add(
                new User("Vladimir", userList_.size(), START_UP_CAPITAL));
        }
    }

    public void printUserList() {
        for (User user : userList_) {
            user.print();
        }
    }

    public void createTransactionList() {
        for (int i = 0; i < UNKNOWN_TEST_NUMBER; ++i) {
            User user1 = userList_.get(i);
            User user2 = userList_.get(i % (UNKNOWN_TEST_NUMBER - i));
            int transactionAmount = 2 * i + 10;
            Transaction tr1 = createTransactionOrReturnNull(
                UUID.randomUUID(), user1, user2, TransactionType.DEBIT,
                transactionAmount);
            Transaction tr2 = createTransactionOrReturnNull(
                UUID.randomUUID(), user2, user1, TransactionType.CREDIT,
                -transactionAmount);

            if ((tr1 != null) && (tr2 != null)) {
                transactionList_.add(tr1);
                transactionList_.add(tr2);
                user1.updateBalance(transactionAmount);
                user2.updateBalance(-transactionAmount);
            }
        }
    }

    public void printTransactionList() {
        for (Transaction tr : transactionList_) {
            tr.print();
        }
    }
}
