
import static user.UserIdsGenerator.getInstance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import user.User;
import user.UserIdsGenerator;

public class Test {

    private static final int UNKNOWN_TEST_NUMBER = 10;
    private static final int START_UP_CAPITAL = 100;

    private ArrayList<User> userList_;

    { userList_ = new ArrayList<User>(); }

    public Test() {}

    public void runTest() {
        printSomeIds();

        createUserList();
        printUserList();

        printSomeIds();

        createUserList();
        printUserList();
    }

    public void createUserList() {
        int userSize = userList_.size();
        for (int i = userSize; i < userSize + UNKNOWN_TEST_NUMBER; ++i) {
            userList_.add(new User("Vladimir" + i, START_UP_CAPITAL));
        }
    }

    public void printUserList() {
        for (User user : userList_) {
            user.print();
        }
        System.out.printf(".\n");
    }

    public void printSomeIds() {
        UserIdsGenerator idGen = UserIdsGenerator.getInstance();
        for (int i = 0; i < UNKNOWN_TEST_NUMBER; ++i) {
            System.out.printf("Some id = %d\n", idGen.generateId());
        }
        System.out.printf(".\n");
    }
}
