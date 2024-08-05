package transaction;

import user.User;
import java.util.UUID;


public class Transaction implements ITransaction {

		private UUID id_;
    private User recepient_;
		private User sender_;
		private TransactionType transferCategory_;
		private int transferAmount_;

		private Transaction(UUID id, User recepient, User sender, TransactionType type, int transferAmount) {
		  id_ = id;
			recepient_ = recepient;
			sender_ = sender;
			transferCategory_ = type;
			transferAmount_ = transferAmount;
		}

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


		public static Transaction createTransactionOrReturnNull(UUID id, User recepient, User sender,
                                                            TransactionType type, int transferAmount) {

				if (!isValidUserBalance(recepient, sender)) {
				    return null; //TODO
				}
				
				if (!isValidTransferAmount(type, transferAmount)) {
				    return null; //TODO
				}

				return new Transaction(id, recepient, sender, type, transferAmount);
    }

		private static boolean isValidUserBalance(User user1, User user2) {
		    return ((user1.getBalance() >= 0) && (user2.getBalance() >= 0));
		}

		private static boolean isValidTransferAmount(TransactionType type, int transferAmount) {
		    return ((type == TransactionType.DEBIT) ? (transferAmount > 0)
								                                : (transferAmount < 0));
				                                       
		}





}
