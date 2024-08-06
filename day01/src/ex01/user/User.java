
package user;

public class User extends HomoSapiens {

    private final int id_;
    private int balance_;

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

    @Override
    public void print() {
        System.out.printf("%d %s has %d\n", id_, super.getName(), balance_);
    }
}
