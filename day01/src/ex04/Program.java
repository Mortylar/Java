
import exception.IllegalTransactionException;
import exception.IllegalUserException;
import exception.TransactionNotFoundException;
import exception.UserNotFoundException;
import java.util.UUID;

class Program {

    public static void main(String[] args)
        throws UserNotFoundException, IllegalUserException,
               TransactionNotFoundException, IllegalTransactionException {
        Test test = new Test();
        test.runTest();
    }
}
