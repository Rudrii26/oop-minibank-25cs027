package model;

// Practical 6: Interface demonstrating a default method for interest calculation
public interface InterestBearing {
    double interestRate();
    long getBalance();

    // Default method providing a concrete implementation within an interface
    default double yearlyInterest() {
        return (getBalance() * interestRate()) / 100.0;
    }
}
