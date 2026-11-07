package exception;

// Practical 8: Base checked exception for all MiniBank domain errors.
// Extending Exception makes this a CHECKED exception (enforced by the compiler).
public class BankException extends Exception {

    public BankException(String message) {
        super(message);
    }

    public BankException(String message, Throwable cause) {
        super(message, cause);
    }
}
