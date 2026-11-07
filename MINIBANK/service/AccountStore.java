package service;

import model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Practical 12: AccountStore demonstrating HashMap, ArrayList, and Comparator
public class AccountStore {

    private final Map<String, Account> accounts = new HashMap<>();
    private final List<String> transactionHistory = new ArrayList<>();

    public void add(Account account) {
        if (account != null) {
            accounts.put(account.getAccountNumber(), account);
        }
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public void logTransaction(String entry) {
        transactionHistory.add(entry);
    }

    // Returns copy of ArrayList holding transaction history (Practical 12)
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    // Sorting accounts descending by balance using a beginner-friendly lambda (Practical 12)
    public List<Account> sortedByBalance() {
        List<Account> sortedList = new ArrayList<>(accounts.values());
        // Simple student lambda: compare balance descending (highest balance first)
        sortedList.sort((a1, a2) -> Long.compare(a2.getBalance(), a1.getBalance()));
        return sortedList;
    }
}
