package exception;

// Practical 8: Custom checked exception thrown when an account number does not exist.
public class AccountNotFoundException extends BankException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
