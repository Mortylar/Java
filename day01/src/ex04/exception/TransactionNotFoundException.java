package exception;

public class TransactionNotFoundException extends Exception {

    public TransactionNotFoundException() { super("Transaction not found"); }
}
