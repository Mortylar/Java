package transaction;

import java.util.UUID;

import user.User;

public class TransactionLinkedList implements TransactionList {

    private Transaction transaction_;
		private TransactionLinkedList next_;

    public TransactionLinkedList() {
		    transaction_ = null;
				next_ = null;
		}

		public TransactionLinkedList(Transaction transaction) {
        transaction_ = null;
				next_ = new TransactionLinkedList();
		    next_.transaction_ = transaction;
				next_.next_ = null;
		}

		@Override
		public void add(Transaction transaction) {
				if (this.next_ == null) {
            next_ = new TransactionLinkedList();
            next_.transaction_ = transaction;
						next_.next_ = null;
				} else {
            TransactionLinkedList tmp = this;
		        while(tmp.next_ != null) {
				        tmp = next_;
				    }
						tmp.next_ = new TransactionLinkedList();
						tmp.next_.transaction_ = transaction;
		    }
		}

		@Override
		public void remove(UUID id) {
        if (next_ != null) {
		        TransactionLinkedList tmp = this.next_;
				    while (tmp.next_ != null) {
				        if (tmp.transaction_.getID() == id) {
						        remove(tmp);
								    break;
						    }
				    }
		    }
		}

    private void remove(TransactionLinkedList current) {
        TransactionLinkedList last = this;
        while (last.next_ != current) {
            last = last.next_;
        }
        last.next_ = current.next_;
    }

		@Override
		public Transaction[] toArray() {
        int size = size();
        Transaction[] array = new Transaction[size];
				TransactionLinkedList tmp = this;
				for (int i = 0; i < size; ++i) {
				    array[i] = tmp.transaction_;
						tmp = tmp.next_;
				}
				return array;
		}

    @Override
		public int size() {
		    int size = 0;
				TransactionLinkedList tmp = this;
				while (tmp.transaction_ != null) {
				    ++size;
						tmp = tmp.next_;
				}
				return size;
		}

		@Override
		public Transaction get(UUID id) {
				TransactionLinkedList tmp = this.next_;
				while(tmp.next_ != null) {
				    if (tmp.transaction_.getID() == id) {
						    return transaction_;
						}
				}
				return this.transaction_;
		}

}
