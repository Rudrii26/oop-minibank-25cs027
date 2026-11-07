package exception;

// Practical 8: Custom checked exception thrown when an amount is negative or non-positive.
public class InvalidAmountException extends BankException {

    public InvalidAmountException(String message) {
        super(message);
    }
}
