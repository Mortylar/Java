
import static transaction.Transaction.createTransactionOrReturnNull;

import java.util.UUID;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionType;
import user.User;
import user.UserIdsGenerator;

import exception.UserNotFoundException;
import exception.IllegalTransactionException;

class Program {

    public static void main(String[] args) throws UserNotFoundException, IllegalTransactionException {
        Test test = new Test();
        test.runTest();
    }
}
