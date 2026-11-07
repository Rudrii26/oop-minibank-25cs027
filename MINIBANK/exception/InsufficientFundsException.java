package exception;

// Practical 8: Custom checked exception representing an over-withdrawal attempt.
// Stores the specific shortfall amount so callers can inform the user.
public class InsufficientFundsException extends BankException {

    private final long shortfall;

    public InsufficientFundsException(String message, long shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public long getShortfall() {
        return shortfall;
    }
}
