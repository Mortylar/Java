package user;

public class UserIdsGenerator {

    private static final UserIdsGenerator instance_ = new UserIdsGenerator();
    private static int lastID_;

    private UserIdsGenerator() { lastID_ = 0; }

    public static UserIdsGenerator getInstance() { return instance_; }

    public int generateId() { return ++lastID_; }
}
