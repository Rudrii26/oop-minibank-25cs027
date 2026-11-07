package model;

import exception.*;
import model.annotation.Id;
import model.annotation.Positive;

import java.io.Serializable;
import java.util.Objects;

// Abstract base Account demonstrating encapsulation, inheritance, interfaces, and serialization
public abstract class Account implements Transactable, InterestBearing, Serializable, Comparable<Account> {
    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;
    private String ownerName;

    @Positive
    protected long balance;
    private boolean active;

    // Static counter for automatic account number generation (Practical 2)
    private static int accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    // Static helper to synchronize counter with loaded accounts after deserialization (Practical 11 & 14)
    public static void syncAccountCounter(int highestId) {
        if (highestId > accountCounter) {
            accountCounter = highestId;
        }
    }

    public static int getAccountCounter() {
        return accountCounter;
    }

    // Constructor with ownerName and opening balance
    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    // Constructor chaining using this() (Practical 2)
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Package-private constructor for persistence reload (preserving existing account numbers)
    Account(String accountNumber, String ownerName, long openingBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    // Unsynchronized deposit helper to demonstrate race conditions in Practical 9
    // Demonstrates non-atomic read-modify-write across multiple threads:
    // 1. Read: Thread reads current balance
    // 2. Context switch: Thread yields CPU before writing
    // 3. Write: Thread overwrites balance with stale + amount, losing other threads' updates
    public void depositUnsynchronized(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive: " + amount);
        }
        long temp = balance;      // Step 1: Read current balance
        Thread.yield();           // Step 2: Yield CPU to trigger context switch
        balance = temp + amount;  // Step 3: Write back updated value (causes lost updates)
    }

    // Synchronized deposit ensures atomic read-modify-write (Practical 9)
    // Only one thread at a time can execute this method on this Account instance
    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive: " + amount);
        }
        balance += amount;
    }

    // Synchronized withdrawal with subtype rules and shortfall reporting (Practicals 8 & 9)
    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive: " + amount);
        }
        if (!canWithdraw(amount)) {
            long shortfall = amount > balance ? amount - balance : 0;
            throw new InsufficientFundsException("Withdrawal failed: short by " + shortfall, shortfall);
        }
        balance -= amount;
        return true;
    }

    // Transfer with try-catch-finally, transaction rollback, and rethrow (Practical 8)
    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("Destination account not found.");
        }
        if (this.equals(to)) {
            throw new InvalidAmountException("Cannot transfer to the same account.");
        }

        boolean debited = false;
        try {
            // Step 1: Withdraw from source account
            this.withdraw(amount);
            debited = true;

            // Step 2: Attempt to deposit into destination account
            to.deposit(amount);
        } catch (BankException e) {
            // Step 3: Transaction Rollback if destination deposit fails
            if (debited) {
                try {
                    this.deposit(amount); // Refund original account
                    System.out.println("[ROLLBACK] Transfer failed; refunded " + amount 
                            + " back to source account " + this.accountNumber);
                } catch (InvalidAmountException ignored) {
                }
            }
            // Step 4: Rethrow the original exception to the caller
            throw e;
        } finally {
            System.out.println("[AUDIT] Transfer attempt from " + this.accountNumber 
                    + " to " + to.accountNumber + " finished.");
        }
    }

    // Deadlock-free transfer using consistent account-number lock ordering (Practical 10)
    public void transferDeadlockFree(Account to, long amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("Destination account not found.");
        }
        if (this.equals(to)) {
            throw new InvalidAmountException("Cannot transfer to the same account.");
        }

        // Lock ordering prevents deadlock by eliminating circular wait:
        // Always lock the smaller account number first, then the larger one
        Account firstLock = this.accountNumber.compareTo(to.accountNumber) < 0 ? this : to;
        Account secondLock = this.accountNumber.compareTo(to.accountNumber) < 0 ? to : this;

        synchronized (firstLock) {
            synchronized (secondLock) {
                boolean debited = false;
                try {
                    this.withdraw(amount);
                    debited = true;
                    to.deposit(amount);
                } catch (BankException e) {
                    if (debited) {
                        try {
                            this.deposit(amount);
                            System.out.println("[ROLLBACK] Transfer failed; refunded " + amount 
                                    + " to " + this.accountNumber);
                        } catch (InvalidAmountException ignored) {
                        }
                    }
                    throw e;
                } finally {
                    System.out.println("[AUDIT] Deadlock-free transfer of " + amount + " from " 
                            + this.accountNumber + " to " + to.accountNumber + " completed.");
                }
            }
        }
    }

    // Abstract methods to be implemented by subclasses (Practical 5)
    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Owner: " + ownerName + ", Balance: " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account other = (Account) o;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    // Comparable implementation for natural sorting by account number (Practical 12)
    @Override
    public int compareTo(Account other) {
        if (other == null) return 1;
        return this.accountNumber.compareTo(other.accountNumber);
    }
}
