package service;

import model.Account;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// Practical 10: Advanced concurrency using an ExecutorService thread pool.
// Manages a fixed pool of 4 worker threads for executing concurrent banking tasks.
public class TransactionProcessor {

    private final ExecutorService executor;
    private final ConcurrentHashMap<String, Account> sharedAccounts;

    public TransactionProcessor() {
        // Requirement: Fixed thread pool with exactly 4 worker threads
        this.executor = Executors.newFixedThreadPool(4);
        this.sharedAccounts = new ConcurrentHashMap<>();
    }

    public void registerAccount(Account account) {
        if (account != null) {
            sharedAccounts.put(account.getAccountNumber(), account);
        }
    }

    public Account getAccount(String accountNumber) {
        return sharedAccounts.get(accountNumber);
    }

    public ConcurrentHashMap<String, Account> getSharedAccounts() {
        return sharedAccounts;
    }

    // Submits a task to the ExecutorService using executor.submit()
    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    // Gracefully stops the thread pool
    public void stop() {
        executor.shutdown(); // Reject new incoming tasks
        try {
            // Wait up to 5 seconds for submitted tasks to complete execution
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Force shutdown if tasks take too long
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
