package transaction;

import java.util.UUID;

interface TransactionList {

    public void add(Transaction transaction);
		public void remove(UUID id);
		public Transaction[] toArray();

    public int size();
    public Transaction get(UUID id);
}
