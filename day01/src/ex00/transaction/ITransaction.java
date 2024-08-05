package transaction;

import user.User;
import java.util.UUID;

interface ITransaction {

    UUID getID();

		User getRecepient();
		User getSender();

		TransactionType getTransactionType();

		int getTransferAmount();

}
