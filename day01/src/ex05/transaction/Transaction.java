package transaction;

import java.util.UUID;
import user.User;

public class Transaction implements ITransaction {

    private UUID id_;
    private User recepient_;
    private User sender_;
    private TransactionType transferCategory_;
    private int transferAmount_;

    private Transaction(UUID id, User recepient, User sender,
                        TransactionType type, int transferAmount) {
        id_ = id;
        recepient_ = recepient;
        sender_ = sender;
        transferCategory_ = type;
        transferAmount_ = transferAmount;
    }

    @Override
    public UUID getID() {
        return id_;
    }

    @Override
    public User getRecepient() {
        return recepient_;
    }

    @Override
    public User getSender() {
        return sender_;
    }

    @Override
    public TransactionType getTransactionType() {
        return transferCategory_;
    }

    @Override
    public int getTransferAmount() {
        return transferAmount_;
    }

    @Override
    public void print() {
        System.out.printf("%s -> %s, %+d, %s, %s\n", sender_.getName(),
                          recepient_.getName(), transferAmount_,
                          ((transferCategory_ == TransactionType.DEBIT)
                               ? "INCOME"
                               : "OUTCOME"),
                          id_.toString());
    }

    public static Transaction
    createTransactionOrReturnNull(UUID id, User recepient, User sender,
                                  TransactionType type, int transferAmount) {

        if (!isValidUserBalance(recepient, sender, transferAmount)) {
            return null;
        }

        if (!isValidTransferAmount(type, transferAmount)) {
            return null;
        }

        return new Transaction(id, recepient, sender, type, transferAmount);
    }

    private static boolean isValidUserBalance(User user1, User user2,
                                              int transactionAmount) {
        return ((user1.getBalance() + transactionAmount >= 0) &&
                (user2.getBalance() - transactionAmount >= 0));
    }

    private static boolean isValidTransferAmount(TransactionType type,
                                                 int transferAmount) {
        return ((type == TransactionType.DEBIT) ? (transferAmount > 0)
                                                : (transferAmount < 0));
    }
}
