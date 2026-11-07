package model;

// Practical 6: Functional interface (SAM) implemented using lambda and anonymous inner class
@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
