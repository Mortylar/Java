package exception;

public class IllegalUserException extends Exception {

    public IllegalUserException() { super("User balance is invalid\n"); }
}
