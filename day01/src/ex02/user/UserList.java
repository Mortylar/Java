package user;

interface UserList {

    public void add(User user);
    public User get(int id) throws UserNotFoundException;
    public User getAt(int index);
    public int size();

    public class UserNotFoundException extends Exception {

        public UserNotFoundException(String message) { super(message); }
    }
}
