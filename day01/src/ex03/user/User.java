
package user;

import transaction.Transaction;
import transaction.TransactionLinkedList;

public class User extends HomoSapiens {

    private final int id_;
    private int balance_;
    private TransactionLinkedList transactions_;

    { transactions_ = new TransactionLinkedList(); }

    public User(String name, int balance) {
        super.setName(name);
        balance_ = balance;
        id_ = UserIdsGenerator.getInstance().generateId();
    }

    public int getID() { return id_; }
    public int getBalance() { return balance_; }

    public void updateBalance(int transactionAmount) {
        balance_ += transactionAmount;
    }

    public boolean checkBalance() { return (balance_ > 0); }

    public void addTransaction(Transaction transaction) {
        transactions_.add(transaction);
    }

    public TransactionLinkedList getTransactionList() { return transactions_; }

    @Override
    public void print() {
        System.out.printf("%d %s has %d\n", id_, super.getName(), balance_);
    }

    public void printTransaction() { transactions_.print(); }
}
