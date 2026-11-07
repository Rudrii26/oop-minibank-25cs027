package service;

import exception.InvalidAmountException;
import model.Account;

// Practical 9: Multithreading worker implementing the standard Runnable interface.
// Performs repeated deposits on a shared Account to test race conditions and synchronization.
public class AccountWorker implements Runnable {

    private final Account account;
    private final int times;
    private final long amount;
    private final boolean synchronizedMode;

    // Default constructor for synchronized deposits
    public AccountWorker(Account account, int times, long amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.synchronizedMode = true;
    }

    // Explicit constructor to choose between synchronized and unsynchronized mode
    public AccountWorker(Account account, int times, long amount, boolean synchronizedMode) {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.synchronizedMode = synchronizedMode;
    }

    @Override
    public void run() {
        System.out.println("[THREAD START] " + Thread.currentThread().getName());
        for (int i = 0; i < times; i++) {
            try {
                if (synchronizedMode) {
                    // Calls thread-safe synchronized deposit method
                    account.deposit(amount);
                } else {
                    // Calls unsynchronized deposit method to expose race conditions
                    account.depositUnsynchronized(amount);
                }
            } catch (InvalidAmountException e) {
                System.out.println("Deposit error on " + Thread.currentThread().getName() + ": " + e.getMessage());
            }
        }
        System.out.println("[THREAD FINISHED] " + Thread.currentThread().getName());
    }
}
