package service;

import exception.*;
import model.*;
import util.StatementFormatter;
import static util.Validator.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BankService {

    private final Repository<Account> accountRepo;
    private final Path logsDir;
    private final Path logFile;

    public BankService(Repository<Account> accountRepo, Path logsDir) {
        this.accountRepo = accountRepo;
        this.logsDir = logsDir;
        this.logFile = logsDir.resolve("transactions.log");
    }

    public Account openAccount(String type, String name, String email, String mobile, long openingBalance, long param)
            throws InvalidAmountException {

        if (!isValidMobile(mobile)) {
            throw new IllegalArgumentException("Invalid mobile number: " + mobile);
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        if (openingBalance < 0) {
            throw new InvalidAmountException("Opening balance cannot be negative.");
        }

        Account account;
        String typeUpper = type.trim().toUpperCase();

        if (typeUpper.contains("SAVINGS") || "1".equals(typeUpper)) {
            account = new SavingsAccount(name, openingBalance, param);
        } else if (typeUpper.contains("CURRENT") || "2".equals(typeUpper)) {
            account = new CurrentAccount(name, openingBalance, param);
        } else if (typeUpper.contains("FIXED") || "3".equals(typeUpper)) {
            account = new FixedDepositAccount(name, openingBalance);
        } else {
            throw new IllegalArgumentException("Unknown account type: " + type);
        }

        accountRepo.save(account);

        try {
            TransactionLog.append(logFile, "OPEN " + account.getAccountNumber() + " " + openingBalance);
        } catch (IOException ignored) {}

        return account;
    }

    public void deposit(String accountNumber, long amount) throws BankException {
        Account acc = findAccountOrThrow(accountNumber);
        acc.deposit(amount);

        try {
            TransactionLog.append(logFile, "DEPOSIT " + accountNumber + " " + amount);
        } catch (IOException ignored) {}
    }

    public boolean withdraw(String accountNumber, long amount) throws BankException {
        Account acc = findAccountOrThrow(accountNumber);
        boolean result = acc.withdraw(amount);

        try {
            TransactionLog.append(logFile, "WITHDRAW " + accountNumber + " " + amount);
        } catch (IOException ignored) {}

        return result;
    }

    public void transfer(String fromAccount, String toAccount, long amount) throws BankException {
        Account from = findAccountOrThrow(fromAccount);
        Account to = findAccountOrThrow(toAccount);

        from.transferDeadlockFree(to, amount);

        try {
            TransactionLog.append(logFile, "TRANSFER " + fromAccount + " " + toAccount + " " + amount);
        } catch (IOException ignored) {}
    }

    public String statement(String accountNumber) throws BankException {
        Account acc = findAccountOrThrow(accountNumber);
        return StatementFormatter.buildStatement(acc);
    }

    public List<Account> listAccounts() {
        return accountRepo.findAll();
    }

    public void loadSavedAccounts(Path file) {
        if (Files.exists(file)) {
            try {
                Account[] loaded = StatePersister.load(file);
                if (loaded != null) {
                    accountRepo.saveAll(loaded);
                    System.out.println("[BankService] Loaded " + loaded.length + " accounts from persistence file: " + file);

                    // Synchronize the Account counter with the highest loaded ID to prevent collisions (Priority 1)
                    int maxId = 0;
                    for (Account acc : loaded) {
                        if (acc != null && acc.getAccountNumber() != null) {
                            String numDigits = acc.getAccountNumber().replaceAll("\\D+", "");
                            if (!numDigits.isEmpty()) {
                                try {
                                    int val = Integer.parseInt(numDigits);
                                    if (val > maxId) {
                                        maxId = val;
                                    }
                                } catch (NumberFormatException ignored) {}
                            }
                        }
                    }
                    Account.syncAccountCounter(maxId);
                    System.out.println("[BankService] Synchronized account counter to: " + maxId);
                }
            } catch (Exception e) {
                System.out.println("[BankService] Warning: Could not load persistence file: " + e.getMessage());
            }
        }
    }

    public void persistAccounts(Path file) {
        List<Account> all = accountRepo.findAll();
        try {
            StatePersister.save(all.toArray(new Account[0]), file);
            System.out.println("[BankService] Successfully persisted " + all.size() + " accounts to: " + file);
        } catch (IOException e) {
            System.out.println("[BankService] Error saving accounts: " + e.getMessage());
        }
    }

    public String generateReport(Path reportFile) {
        try {
            // Calculate total balance across all accounts currently in repository (Priority 3)
            long totalBalances = 0;
            for (Account a : accountRepo.findAll()) {
                totalBalances += a.getBalance();
            }
            return ReportGenerator.generateReport(logsDir, reportFile, totalBalances);
        } catch (IOException e) {
            return "Error generating report: " + e.getMessage();
        }
    }

    private Account findAccountOrThrow(String accountNumber) throws AccountNotFoundException {
        Account acc = accountRepo.findById(accountNumber);
        if (acc == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return acc;
    }
}

