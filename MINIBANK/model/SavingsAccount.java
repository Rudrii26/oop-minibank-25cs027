package model;

// Practical 5: SavingsAccount subclass demonstrating inheritance and polymorphism
// Practical 6: Implements Premium marker interface
public class SavingsAccount extends Account implements Premium {

    private static final long serialVersionUID = 1L;

    private long minBalance;

    // Constructor calling super(...) to initialize inherited fields (Practical 5)
    public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
        super(ownerName, openingBalance);
        this.minBalance = minBalance;
    }

    // Dynamic method dispatch returns 4.0% interest rate
    @Override
    public double interestRate() {
        return 4.0;
    }

    // Withdrawal rule: balance must stay >= minBalance
    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && (balance - amount) >= minBalance;
    }

    public long getMinBalance() {
        return minBalance;
    }
}
