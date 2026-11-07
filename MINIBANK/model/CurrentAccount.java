package model;

// Practical 5: CurrentAccount subclass with overdraft facility and 0% interest
public class CurrentAccount extends Account {

    private static final long serialVersionUID = 1L;

    private long overdraftLimit;

    // Constructor calling super(...) to initialize inherited fields (Practical 5)
    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    // Dynamic method dispatch returns 0.0% interest rate
    @Override
    public double interestRate() {
        return 0.0;
    }

    // Withdrawal rule: balance can go negative down to -overdraftLimit
    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && (balance - amount) >= -overdraftLimit;
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }
}
