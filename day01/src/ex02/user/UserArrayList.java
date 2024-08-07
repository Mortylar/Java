package user;

public class UserArrayList implements UserList {

    private static final int DEFAULT_CAPASITY = 10;
    private static final int INCREASE_VALUE = 2;

    private int size_;
    private int capasity_;
    private Object[] data_;

    public UserArrayList() {
        size_ = 0;
        capasity_ = DEFAULT_CAPASITY;
        allocate(capasity_);
    }

    public UserArrayList(int capasity) {
        capasity_ = ((capasity > 0) ? capasity : DEFAULT_CAPASITY);
        size_ = 0;
        allocate(capasity_);
    }

    private void allocate(int allocateSize) {
        data_ = new Object[allocateSize];
    }

    private void reallocate() {
        capasity_ *= INCREASE_VALUE;
        Object[] newData = new Object[capasity_];

        for (int i = 0; i < size_; ++i) {
            newData[i] = data_[i];
        }
        data_ = newData;
    }

    @Override
    public void add(User user) {
        if (size_ >= capasity_) {
            reallocate();
        }

        data_[size_++] = user;
    }

    @Override
    public User get(int id) throws UserNotFoundException {
        int index = 0;
        while (index < size_) {
            if (((User)data_[index]).getID() == id) {
                return ((User)data_[index]);
            }
            ++index;
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public User getAt(int index) {
        return ((User)data_[index]);
    }

    @Override
    public int size() {
        return size_;
    }

    public int getCapasity() { return capasity_; }
}
