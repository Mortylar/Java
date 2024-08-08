
import java.util.UUID;

import static transaction.Transaction.createTransactionOrReturnNull;

import user.User;
import user.UserArrayList;

import transaction.Transaction;
import transaction.TransactionType;
import transaction.TransactionLinkedList;

import exception.TransactionNotFoundException;
import exception.UserNotFoundException;
import exception.IllegalTransactionException;

public class TransactionService {

    private UserArrayList userList_;
		private TransactionLinkedList trList_;  //TODO is needed?

		{
        userList_ = new UserArrayList();
				trList_ = new TransactionLinkedList();
		}

    public TransactionService() {
		}

    public void addUser(User user) {
		    userList_.add(user);
		}

		public int getUserBalance(int userID) throws UserNotFoundException {
		    return (userList_.get(userID)).getBalance();
		}

		public void transactionPerform(int id1, int id2, int transactionAmount) throws UserNotFoundException, IllegalTransactionException {
		    UUID transactionID = UUID.randomUUID();
				User user1 = userList_.get(id1);
				User user2 = userList_.get(id2);

				Transaction tr1 = createTransactionOrReturnNull(transactionID, user1, user2, TransactionType.DEBIT, transactionAmount);
				Transaction tr2 = createTransactionOrReturnNull(transactionID, user2, user1, TransactionType.CREDIT, -transactionAmount);
		    
        if ((tr1 == null) || (tr2 == null)) {
				    throw new IllegalTransactionException();
				}

				user1.addTransaction(tr1);
				user1.addTransaction(tr2);
        user1.updateBalance(transactionAmount);

				user2.addTransaction(tr1);
				user2.addTransaction(tr2);
        user2.updateBalance(transactionAmount);
		}

		public Transaction[] getTransactions(int userID) throws UserNotFoundException {
		    return ((userList_.get(userID)).getTransactionList()).toArray();
		}

    public void removeTransaction(UUID transactionID, int userID) throws UserNotFoundException, TransactionNotFoundException {
		   ((userList_.get(userID)).getTransactionList()).remove(transactionID);
		}

    public void printUserList() {
		    userList_.print();
		}
}
