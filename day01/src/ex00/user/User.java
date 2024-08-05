
package user;

public class User extends HomoSapiens {

    private int id_;
		private int balance_;

		public User(String name, int id, int balance) {
        super.setName(name);
				balance_ = balance;
        id_ = id;
		}

    public int getID() { return id_; }
    public int getBalance() { return balance_; }

		@Override
		public void print() {
		    System.out.printf("%d %s has %d\n", id_, super.getName(), balance_);
		}



}
