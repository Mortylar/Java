package transaction;

import exception.TransactionNotFoundException;
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

    public TransactionLinkedList add(TransactionLinkedList other) {
        TransactionLinkedList newList = new TransactionLinkedList();
        TransactionLinkedList tmp = this.next_;
        while (tmp != null) {
            newList.add(tmp.transaction_);
            tmp = tmp.next_;
        }
        tmp = other.next_;
        while (tmp != null) {
            newList.add(tmp.transaction_);
            tmp = tmp.next_;
        }
        return newList;
    }

    @Override
    public void add(Transaction transaction) {
        if (this.next_ == null) {
            next_ = new TransactionLinkedList();
            next_.transaction_ = transaction;
            next_.next_ = null;
        } else {
            TransactionLinkedList tmp = this.next_;
            this.next_ = new TransactionLinkedList();
            this.next_.transaction_ = transaction;
            this.next_.next_ = tmp;
        }
    }

    @Override
    public void remove(UUID id) throws TransactionNotFoundException {
        if (next_ != null) {
            TransactionLinkedList tmp = this.next_;
            while (tmp != null) {
                if (tmp.transaction_.getID().equals(id)) {
                    remove(tmp);
                    return;
                }
                tmp = tmp.next_;
            }
        }
        throw new TransactionNotFoundException();
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
        if (size > 0) {
            TransactionLinkedList tmp = this.next_;
            for (int i = 0; i < size; ++i) {
                array[i] = tmp.transaction_;
                tmp = tmp.next_;
            }
        }
        return array;
    }

    @Override
    public int size() {
        int size = 0;
        TransactionLinkedList tmp = this;
        while (tmp.next_ != null) {
            ++size;
            tmp = tmp.next_;
        }
        return size;
    }

    @Override
    public Transaction get(UUID id) {
        TransactionLinkedList tmp = this.next_;
        while (tmp != null) {
            if (tmp.transaction_.getID().equals(id)) {
                return tmp.transaction_;
            }
            tmp = tmp.next_;
        }
        return this.transaction_;
    }

    public Transaction getFirst() {
        if (next_ != null) {
            return next_.transaction_;
        }
        return transaction_;
    }

    @Override
    public void print() {
        TransactionLinkedList tmp = this.next_;
        if (tmp == null) {
            System.out.print("Transaction list is empty\n");
        } else {
            while (tmp != null) {
                tmp.transaction_.print();
                tmp = tmp.next_;
            }
        }
    }
}
