
import static transaction.Transaction.createTransactionOrReturnNull;

import java.util.UUID;
import transaction.Transaction;
import transaction.TransactionType;
import user.User;

class Program {

    public static void main(String[] args) {
        User user1 = new User("Vova", 1, 1);
        User user2 = new User("Vova", 2, 1);

        Test test = new Test();
        //        test.createUserList();
        test.runTest();

        //        Transaction tr = createTransactionOrReturnNull(
        //            UUID.randomUUID(), user1, user2, TransactionType.CREDIT,
        //            -1);
        //        if (tr == null) {
        //            System.out.println("null");
        //        } else {
        //            tr.print();
        //       }

        // for (int i = 0; i < 10; ++i) {
        //     UUID id = UUID.randomUUID();
        //     System.out.printf("\nid = %s\n", id.toString());
        // }
    }
}
