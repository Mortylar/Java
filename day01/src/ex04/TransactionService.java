
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

		{
        userList_ = new UserArrayList();
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
        user2.updateBalance(-transactionAmount);
		}

		public Transaction[] getTransactions(int userID) throws UserNotFoundException {
		    return ((userList_.get(userID)).getTransactionList()).toArray();
		}

    public void removeTransaction(UUID transactionID, int userID) throws UserNotFoundException, TransactionNotFoundException {
		   ((userList_.get(userID)).getTransactionList()).remove(transactionID);//TODO remove all transactions with this id
		}

    public void printUserList() {
		    userList_.print();
		}

		public void printTransactions() throws UserNotFoundException {
		    for (int i = 0; i < userList_.size(); ++i) {
				    System.out.printf("%s transactions:\n", userList_.get(i + 1).getName());
						//Transaction[] array = getTransactions(i);
						//for (int j = 0; j < array.size(); ++j) {
						//    array[j].print();
						//}
            userList_.get(i + 1).getTransactionList().print();
						System.out.print("\n");
				}
		}
}
