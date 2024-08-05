
import java.util.UUID;

import user.User;

import transaction.Transaction;
import transaction.TransactionType;
import static transaction.Transaction.createTransactionOrReturnNull;

class Program {

    public static void main(String[] args) {
        User user1 = new User("Vova", 1, 1);
        User user2 = new User("Vova", 2, 1);

        Transaction tr = createTransactionOrReturnNull(new UUID(0,0), user1, user2, TransactionType.DEBIT, 0);
        if (tr == null) {
				  System.out.println("null");
				} else {
				  System.out.println("not null");
				}
    }
}
