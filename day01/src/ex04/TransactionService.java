
import static transaction.Transaction.createTransactionOrReturnNull;

import exception.IllegalTransactionException;
import exception.TransactionNotFoundException;
import exception.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionType;
import user.User;
import user.UserArrayList;

public class TransactionService {

    private UserArrayList userList_;

    { userList_ = new UserArrayList(); }

    public TransactionService() {}

    public void addUser(User user) { userList_.add(user); }

    public int getUserBalance(int userID) throws UserNotFoundException {
        return (userList_.get(userID)).getBalance();
    }

    public void transactionPerform(int id1, int id2, int transactionAmount)
        throws UserNotFoundException, IllegalTransactionException {
        UUID transactionID = UUID.randomUUID();
        User user1 = userList_.get(id1);
        User user2 = userList_.get(id2);

        Transaction tr1 = createTransactionOrReturnNull(
            transactionID, user1, user2, TransactionType.DEBIT,
            transactionAmount);
        Transaction tr2 = createTransactionOrReturnNull(
            transactionID, user2, user1, TransactionType.CREDIT,
            -transactionAmount);

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

    public Transaction[] getTransactions(int userID)
        throws UserNotFoundException {
        return ((userList_.get(userID)).getTransactionList()).toArray();
    }

    public void removeTransaction(UUID transactionID, int userID)
        throws UserNotFoundException, TransactionNotFoundException {
        while (
            ((userList_.get(userID)).getTransactionList()).get(transactionID) !=
            null) {
            ((userList_.get(userID)).getTransactionList())
                .remove(transactionID);
        }
    }

    public void printUserList() { userList_.print(); }

    public void printTransactions() throws UserNotFoundException {
        for (int i = 0; i < userList_.size(); ++i) {
            System.out.printf("%s transactions:\n",
                              userList_.get(i + 1).getName());
            userList_.get(i + 1).getTransactionList().print();
            System.out.print("\n");
        }
    }

    public Transaction[] validate() throws UserNotFoundException {
        Map<UUID, Transaction> uniqTransactions =
            new HashMap<UUID, Transaction>();
        for (int i = 0; i < userList_.size(); ++i) {
            int userID = i + 1;
            int transactionCount =
                ((userList_.get(userID)).getTransactionList()).size();
            Transaction[] array =
                ((userList_.get(userID)).getTransactionList()).toArray();
            for (int j = 0; j < transactionCount; ++j) {
                uniqTransactions.put(array[j].getID(), array[j]);
            }
        }
        Object[] defArray = uniqTransactions.values().toArray();
        Transaction[] resArray = new Transaction[defArray.length];
        System.arraycopy(defArray, 0, resArray, 0, defArray.length);
        return resArray;
    }
}
