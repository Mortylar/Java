package user;

import exception.UserNotFoundException;

interface UserList {

    public void add(User user);
    public User get(int id) throws UserNotFoundException;
    public User getAt(int index);
    public int size();
}
