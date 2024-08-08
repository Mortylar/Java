
import static transaction.Transaction.createTransactionOrReturnNull;

import java.util.UUID;

import user.User;
import user.UserIdsGenerator;

import transaction.Transaction;
import transaction.TransactionType;
import transaction.TransactionLinkedList;


class Program {

    public static void main(String[] args) {
        TransactionLinkedList tr_list;

        User user = new User("Vova", 100);
				Transaction tr = createTransactionOrReturnNull(UUID.randomUUID(), user, user, TransactionType.DEBIT, 10);

        user.print();
				tr.print();

				tr_list = new TransactionLinkedList();
				tr_list.add(tr);
				tr_list.add(tr);

				System.out.printf("\nlist size == %d\n", tr_list.size());

				tr_list.remove(tr.getID());


				System.out.printf("\nlist size == %d\n", tr_list.size());

    }
}
