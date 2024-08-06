package transaction;

import java.util.UUID;
import user.User;

interface ITransaction {

    UUID getID();

    User getRecepient();
    User getSender();

    TransactionType getTransactionType();

    int getTransferAmount();

    void print();
}
