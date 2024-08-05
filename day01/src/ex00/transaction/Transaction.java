package transaction;

import user.User;
import java.util.UUID;


public class Transaction implements ITransaction {

		private UUID id_;
    private User recepient_;
		private User sender_;
		private TransactionType transferCategory_;
		private int transferAmount_;

    @Override
    public UUID getID() { return id_; }

    @Override
    public User getRecepient() { return recepient_; }

    @Override
    public User getSender() { return sender_; }

    @Override
    public TransactionType getTransactionType() { return transferCategory_; }

    @Override
    public int getTransferAmount() { return transferAmount_; }


}
