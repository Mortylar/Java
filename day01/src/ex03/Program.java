
import static transaction.Transaction.createTransactionOrReturnNull;

import java.util.UUID;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionType;
import user.User;
import user.UserIdsGenerator;

class Program {

    public static void main(String[] args) {
        Test test = new Test();
        test.runTest();
    }
}
