
import static transaction.Transaction.createTransactionOrReturnNull;

import java.util.UUID;
import transaction.Transaction;
import transaction.TransactionType;
import user.User;

class Program {

    public static void main(String[] args) {
        Test test = new Test();
        test.runTest();
    }
}
