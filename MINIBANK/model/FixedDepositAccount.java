package model;

// Practical 5: FixedDepositAccount subclass with locked withdrawals and 7% interest
public class FixedDepositAccount extends Account {

    private static final long serialVersionUID = 1L;

    // Constructor calling super(...) to initialize inherited fields (Practical 5)
    public FixedDepositAccount(String ownerName, long openingBalance) {
        super(ownerName, openingBalance);
    }

    // Dynamic method dispatch returns 7.0% interest rate
    @Override
    public double interestRate() {
        return 7.0;
    }

    // Fixed deposits are locked: withdrawals are not allowed
    @Override
    public boolean canWithdraw(long amount) {
        return false;
    }
}
