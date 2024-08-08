package transaction;

import exception.TransactionNotFoundException;
import java.util.UUID;

interface TransactionList {

    public void add(Transaction transaction);
    public void remove(UUID id) throws TransactionNotFoundException;
    public Transaction[] toArray();

    public int size();
    public Transaction get(UUID id);

    public void print();
}
