
import static user.UserIdsGenerator.getInstance;

// import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import user.User;
import user.UserArrayList;
import user.UserIdsGenerator;

public class Test {

    private static final int UNKNOWN_TEST_NUMBER = 10;
    private static final int START_UP_CAPITAL = 100;

    private UserArrayList userList_;

    { userList_ = new UserArrayList(); }

    public Test() {}

    public void runTest() {
        createUserList();
        printUserList();

        System.out.printf("\n<================>\n");

        reversePrintUserList();
    }

    public void createUserList() {
        for (int i = 0; i < UNKNOWN_TEST_NUMBER + 1; ++i) {
            userList_.add(new User("Vladimir" + i, START_UP_CAPITAL));
        }
    }

    public void printUserList() {
        for (int i = 0; i < userList_.size(); ++i) {
            userList_.getAt(i).print();
        }
        System.out.printf(".\n");
    }

    public void reversePrintUserList() {
        for (int i = 1; i <= userList_.size(); ++i) {
            userList_.getAt(userList_.size() - i).print();
        }
        System.out.printf(".\n");
    }
}
