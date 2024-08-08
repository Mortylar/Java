
import static transaction.Transaction.createTransactionOrReturnNull;
import static user.UserIdsGenerator.getInstance;

import exception.TransactionNotFoundException;
import exception.IllegalTransactionException;
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
    private TransactionService service_;

    {
        service_ = new TransactionService();
        userList_ = new UserArrayList();
        trList_ = new TransactionLinkedList();
    }

    public Test() {}

    public void runTest() throws UserNotFoundException, IllegalTransactionException {
        createUserList();
				service_.printUserList();
				printSeparator();
				
				createTransactions();
				service_.printUserList();
				printSeparator();
        service_.printTransactions();
				printSeparator();

    }

		public void createUserList() {
		    User user = new User("Vova", 100);
				service_.addUser(user);

				user = new User("Vladimir", 1000);
				service_.addUser(user);

				user = new User("Ivan", 1000);
				service_.addUser(user);

				user = new User("Sanya", 10);
				service_.addUser(user);
		}

		public void createTransactions() throws UserNotFoundException, IllegalTransactionException {
		    service_.transactionPerform(1, 2, 10);
		    service_.transactionPerform(1, 3, 10);
		    service_.transactionPerform(1, 4, 10);
		    service_.transactionPerform(4, 2, 100);
		    service_.transactionPerform(3, 2, 100);
		}

    public void printSeparator() {
		    System.out.println(".");
		}

}
