
import static transaction.Transaction.createTransactionOrReturnNull;

import exception.IllegalTransactionException;
import exception.TransactionNotFoundException;
import exception.UserNotFoundException;
import java.util.UUID;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionType;
import user.User;
import user.UserIdsGenerator;

class Program {

    public static void main(String[] args) throws UserNotFoundException,
                                                  TransactionNotFoundException,
                                                  IllegalTransactionException {
        Menu menu = new Menu(args);
        menu.run();
    }
}
