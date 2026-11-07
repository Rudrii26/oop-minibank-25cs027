package service;

import exception.*;
import model.*;
import util.AnnotationValidator;
import util.BankTransactionSession;
import util.StatementFormatter;
import static util.Validator.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MiniBank {
    public static void main(String[] args) {

        BankInfo bank = new BankInfo("MiniBank", "Nadiad Branch");

        System.out.println("==================================");
        System.out.println("          " + bank.name());
        System.out.println("          " + bank.branch());
        System.out.println("==================================");

        Customer.Address address = new Customer.Address(
                "College Road", "Nadiad", "387001"
        );

        Customer c1 = new Customer("Riya", "riya@gmail.com", "9876543210", address);
        Customer c2 = new Customer("Aarav", "aarav@gmail.com", "8765432109");
        Customer c3 = new Customer("Neha", "neha@gmail.com", "7654321098");

        // ========== PRACTICAL 2: ENCAPSULATION & STATIC ID GENERATION ==========
        System.out.println("\n--- Customer Details (Practical 2) ---");
        System.out.println(c1.getCustomerId() + " - " + c1.getName());
        System.out.println(c2.getCustomerId() + " - " + c2.getName());
        System.out.println(c3.getCustomerId() + " - " + c3.getName());

        Account[] accounts = {
                new SavingsAccount(c1.getName(), 5000, 1000),
                new CurrentAccount(c2.getName(), 3000, 2000),
                new FixedDepositAccount(c3.getName(), 10000)
        };

        System.out.println("\n--- Account Operations (Practical 2) ---");
        try {
            accounts[0].deposit(2000);
            System.out.println("Balance after deposit 2000: " + accounts[0].getBalance());

            boolean wResult = accounts[0].withdraw(3000);
            System.out.println("Withdrawal of 3000 successful: " + wResult);
            System.out.println("Balance after withdrawal: " + accounts[0].getBalance());
        } catch (BankException e) {
            System.out.println("Operation error: " + e.getMessage());
        }

        // ========== PRACTICAL 3: OBJECT METHODS, NESTED CLASSES & CLONING ==========
        System.out.println("\n--- Object Methods & Cloning (Practical 3) ---");
        System.out.println("toString() output: " + accounts[0]);
        System.out.println("Address Details: Line=" + c1.getAddress().getLine()
                + ", City=" + c1.getAddress().getCity()
                + ", Pincode=" + c1.getAddress().getPincode());

        System.out.println("Are AC0001 and AC0002 equal? " + accounts[0].equals(accounts[1]));
        System.out.println("HashCode 1: " + accounts[0].hashCode());
        System.out.println("HashCode 2: " + accounts[1].hashCode());

        Customer copiedCustomer = c1.clone();
        System.out.println("Original Customer: " + c1.getName());
        System.out.println("Copied Customer: " + copiedCustomer.getName());
        System.out.println("Same object reference? " + (c1 == copiedCustomer));

        System.out.println("Is account an Account instance? " + (accounts[0] instanceof Account));
        System.out.println("Is customer a Customer instance? " + (c1 instanceof Customer));

        // ========== PRACTICAL 4: STRINGS, REGEX & COMMAND PARSING ==========
        System.out.println("\n--- Validation & Command Parsing (Practical 4) ---");
        System.out.println("Mobile Valid (9876543210): " + isValidMobile("9876543210"));
        System.out.println("Mobile Valid (12345): " + isValidMobile("12345"));
        System.out.println("Email Valid (abc@gmail.com): " + isValidEmail("abc@gmail.com"));
        System.out.println("PAN Valid (ABCDE1234F): " + isValidPan("ABCDE1234F"));
        System.out.println("IFSC Valid (SBIN0001234): " + isValidIfsc("SBIN0001234"));
        System.out.println("Amount Valid (500): " + isValidAmount("500"));

        Command command = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Parsed Command: Type=" + command.type()
                + ", Account=" + command.accountNumber()
                + ", Amount=" + command.amount());

        System.out.println("\n" + StatementFormatter.buildStatement(accounts[0]));

        // ========== PRACTICAL 5: INHERITANCE, ABSTRACT CLASSES & POLYMORPHISM ==========
        System.out.println("\n--- Practical 5 - Subtypes & Polymorphism ---");
        for (Account account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Owner: " + account.getOwnerName());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Interest Rate: " + account.interestRate() + "%");
            System.out.println("Yearly Interest: " + account.yearlyInterest());

            if (account instanceof SavingsAccount sa) {
                System.out.println("Account Type: Savings Account (Min Balance: " + sa.getMinBalance() + ")");
            } else if (account instanceof CurrentAccount ca) {
                System.out.println("Account Type: Current Account (Overdraft Limit: " + ca.getOverdraftLimit() + ")");
            } else if (account instanceof FixedDepositAccount) {
                System.out.println("Account Type: Fixed Deposit Account (Locked)");
            }

            if (account instanceof Premium) {
                System.out.println("Status: Premium Account Holder");
            }
            System.out.println();
        }

        try {
            accounts[0].withdraw(10000);
        } catch (InsufficientFundsException e) {
            System.out.println("SavingsAccount withdrawal below min balance (10000): false (Balance: " + accounts[0].getBalance() + ")");
        } catch (BankException e) {
            System.out.println("Operation error: " + e.getMessage());
        }

        try {
            boolean caResult = accounts[1].withdraw(4000);
            System.out.println("CurrentAccount withdrawal within overdraft limit (4000): "
                    + caResult + " (Balance: " + accounts[1].getBalance() + ")");
        } catch (BankException e) {
            System.out.println("CurrentAccount error: " + e.getMessage());
        }

        try {
            accounts[2].withdraw(1000);
        } catch (InsufficientFundsException e) {
            System.out.println("FixedDepositAccount withdrawal locked (1000): false (Balance: " + accounts[2].getBalance() + ")");
        } catch (BankException e) {
            System.out.println("FixedDeposit error: " + e.getMessage());
        }

        System.out.println("\n========== PRACTICAL 6 - WITHDRAW RULE TESTING ==========");

        // Functional interface implementation 1: Anonymous class
        WithdrawRule anonRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount <= 5000;
            }
        };

        // Functional interface implementation 2: Lambda expression
        WithdrawRule lambdaRule = (account, amount) -> amount <= 5000;

        System.out.println("Rule condition: amount <= 5000");
        System.out.println("Anonymous class allow(accounts[0], 3000): " + anonRule.allow(accounts[0], 3000));
        System.out.println("Lambda expression allow(accounts[0], 3000): " + lambdaRule.allow(accounts[0], 3000));
        System.out.println("Anonymous class allow(accounts[0], 8000): " + anonRule.allow(accounts[0], 8000));
        System.out.println("Lambda expression allow(accounts[0], 8000): " + lambdaRule.allow(accounts[0], 8000));
        System.out.println("Both implementations produce identical results: "
                + (anonRule.allow(accounts[0], 3000) == lambdaRule.allow(accounts[0], 3000)
                && anonRule.allow(accounts[0], 8000) == lambdaRule.allow(accounts[0], 8000)));

        System.out.println("\n========== PRACTICAL 7 - ANNOTATION VALIDATION ==========");

        // Create an invalid account with negative balance -100
        Account invalidAccount = new SavingsAccount("InvalidUser", -100, 1000);
        String[] invalidErrors = AnnotationValidator.validate(invalidAccount);
        System.out.println("Validating invalid Account (balance = -100):");
        for (String error : invalidErrors) {
            System.out.println("Validation Error: " + error);
        }

        // Validate a valid account
        String[] validErrors = AnnotationValidator.validate(accounts[0]);
        System.out.println("Validating valid Account (balance = " + accounts[0].getBalance() + "):");
        System.out.println("Errors count: " + validErrors.length);

        System.out.println("\n========== PRACTICAL 8 - EXCEPTION HANDLING & FAULT TOLERANCE ==========");

        // Step 1: Handling InsufficientFundsException with custom shortfall field
        Account testExAcc = new SavingsAccount("Suresh", 1000, 0);
        try {
            System.out.println("Calling withdraw(5000) on account with balance 1000:");
            testExAcc.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: short by " + e.getShortfall());
            System.out.println("Balance after failed withdrawal: " + testExAcc.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount error: " + e.getMessage());
        }

        // Step 2: Handling InvalidAmountException on negative deposit
        try {
            System.out.println("\nCalling deposit(-500):");
            testExAcc.deposit(-500);
        } catch (InvalidAmountException e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        }

        // Step 3: Handling AccountNotFoundException on null or missing target account
        try {
            System.out.println("\nCalling transfer() with null destination account:");
            accounts[0].transfer(null, 500);
        } catch (AccountNotFoundException e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        } catch (BankException e) {
            System.out.println("Bank error: " + e.getMessage());
        }

        // Step 4: Standard transfer with try-catch-finally
        try {
            System.out.println("\nExecuting valid transfer of 500 from AC0001 to AC0002:");
            accounts[0].transfer(accounts[1], 500);
            System.out.println("Transfer successful! AC0001 balance: " + accounts[0].getBalance()
                    + ", AC0002 balance: " + accounts[1].getBalance());
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }

        // Step 5: Transaction Rollback demonstration when destination deposit fails
        System.out.println("\nTesting Transaction Rollback (destination deposit failure):");
        Account rollbackSender = new SavingsAccount("RollbackSender", 2000, 0);
        // Anonymous subclass representing a restricted destination account that rejects deposits
        Account failingTarget = new SavingsAccount("FailingReceiver", 500, 0) {
            @Override
            public synchronized void deposit(long amt) throws InvalidAmountException {
                throw new InvalidAmountException("Destination account is frozen / unable to accept deposits.");
            }
        };

        long initialSenderBalance = rollbackSender.getBalance();
        System.out.println("Sender balance before transfer: " + initialSenderBalance);
        try {
            System.out.println("Attempting transfer of 500 to restricted account...");
            rollbackSender.transfer(failingTarget, 500);
        } catch (BankException e) {
            System.out.println("Caught expected transfer failure: " + e.getMessage());
        }
        System.out.println("Sender balance after rollback: " + rollbackSender.getBalance() 
                + " (Expected: " + initialSenderBalance + ")");
        System.out.println("Rollback verification successful: " 
                + (rollbackSender.getBalance() == initialSenderBalance));

        // Step 6: Test try-with-resources using custom AutoCloseable session
        System.out.println("\nTesting try-with-resources AutoCloseable session:");
        try (BankTransactionSession session = new BankTransactionSession("Audit-Session-101")) {
            System.out.println("Executing secure session operations...");
        }

        System.out.println("\n========== PRACTICAL 9 - THREADS & SYNCHRONISATION ==========");

        int threadCount = 10;
        int depositsPerThread = 1000;
        long depositAmount = 1;

        // Part 1: Testing WITHOUT Synchronization (Exposing the Race Condition)
        System.out.println("\n--- Step 1: Testing Without Synchronization (Race Condition) ---");
        Account raceAccount = new SavingsAccount("RaceHazardUser", 0, 0);
        Thread[] raceWorkers = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            raceWorkers[i] = new Thread(
                    new AccountWorker(raceAccount, depositsPerThread, depositAmount, false),
                    "UnsyncWorker-" + (i + 1)
            );
        }

        System.out.println("Starting 10 unsynchronized threads (each depositing 1 x 1000 times)...");
        for (Thread worker : raceWorkers) {
            worker.start();
        }

        for (Thread worker : raceWorkers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Final balance WITHOUT synchronization: " + raceAccount.getBalance() + " (Expected: 10000)");
        System.out.println("Race Condition Explanation: Non-atomic read-modify-write causes concurrent threads to overwrite each other, leading to lost updates.");

        // Part 2: Testing WITH Synchronization (Race Condition Resolved)
        System.out.println("\n--- Step 2: Testing WITH Synchronization (Thread-Safe) ---");
        Account syncAccount = new SavingsAccount("ThreadSafeUser", 0, 0);
        Thread[] syncWorkers = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            syncWorkers[i] = new Thread(
                    new AccountWorker(syncAccount, depositsPerThread, depositAmount, true),
                    "SyncWorker-" + (i + 1)
            );
        }

        System.out.println("Starting 10 synchronized threads (each depositing 1 x 1000 times)...");
        for (Thread worker : syncWorkers) {
            worker.start();
        }

        for (Thread worker : syncWorkers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Final balance WITH synchronization: " + syncAccount.getBalance() + " (Expected: 10000)");
        System.out.println("Synchronisation test passed: " + (syncAccount.getBalance() == 10000));

        System.out.println("\n========== PRACTICAL 10 - CONCURRENCY (EXECUTOR & DEADLOCK PREVENTION) ==========");

        // Part 1: TransactionProcessor fixed thread pool
        System.out.println("\n--- Step 1: Managed Thread Pool (TransactionProcessor) ---");
        TransactionProcessor processor = new TransactionProcessor();
        Account poolAccount = new SavingsAccount("PoolUser", 5000, 0);

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            processor.submit(() -> {
                try {
                    poolAccount.deposit(100);
                    System.out.println("[POOL TASK " + taskId + "] Deposited 100 by " + Thread.currentThread().getName());
                } catch (InvalidAmountException e) {
                    System.out.println("Pool deposit error: " + e.getMessage());
                }
            });
        }
        processor.stop();
        System.out.println("TransactionProcessor stopped. Balance: " + poolAccount.getBalance());

        // Part 2: Producer-Consumer coordination with wait/notify
        System.out.println("\n--- Step 2: Producer-Consumer (TransactionBuffer) ---");
        TransactionBuffer txBuffer = new TransactionBuffer(2);

        Thread producerThread = new Thread(() -> {
            String[] transactions = {"TX101-DEPOSIT-100", "TX102-WITHDRAW-50", "TX103-TRANSFER-30"};
            for (String tx : transactions) {
                try {
                    txBuffer.produce(tx);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "TxProducer");

        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    txBuffer.consume();
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "TxConsumer");

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Producer/Consumer interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        // Part 3: Genuine Deadlock Demonstration using Opposite Lock Ordering
        System.out.println("\n--- Step 3: Deadlock Demonstration (Opposite Lock Ordering) ---");
        Account deadlockAccA = new SavingsAccount("DeadlockUserA", 1000, 0);
        Account deadlockAccB = new SavingsAccount("DeadlockUserB", 1000, 0);

        System.out.println("Starting deadlock demo between " 
                + deadlockAccA.getAccountNumber() + " and " + deadlockAccB.getAccountNumber() + "...");

        // Thread 1: Locks Account A first, then attempts to lock Account B
        Thread deadlockThread1 = new Thread(() -> {
            synchronized (deadlockAccA) {
                System.out.println("[Thread 1] Acquired lock on " + deadlockAccA.getAccountNumber() 
                        + ", waiting to acquire " + deadlockAccB.getAccountNumber() + "...");
                try {
                    Thread.sleep(100); // Give Thread 2 time to acquire Account B
                } catch (InterruptedException ignored) {}
                synchronized (deadlockAccB) {
                    System.out.println("[Thread 1] Acquired lock on " + deadlockAccB.getAccountNumber());
                }
            }
        }, "Deadlock-Worker-1");

        // Thread 2: Locks Account B first, then attempts to lock Account A (Opposite Order)
        Thread deadlockThread2 = new Thread(() -> {
            synchronized (deadlockAccB) {
                System.out.println("[Thread 2] Acquired lock on " + deadlockAccB.getAccountNumber() 
                        + ", waiting to acquire " + deadlockAccA.getAccountNumber() + "...");
                try {
                    Thread.sleep(100); // Give Thread 1 time to acquire Account A
                } catch (InterruptedException ignored) {}
                synchronized (deadlockAccA) {
                    System.out.println("[Thread 2] Acquired lock on " + deadlockAccA.getAccountNumber());
                }
            }
        }, "Deadlock-Worker-2");

        // Set both as DAEMON threads so they will never prevent JVM shutdown or freeze the app
        deadlockThread1.setDaemon(true);
        deadlockThread2.setDaemon(true);

        deadlockThread1.start();
        deadlockThread2.start();

        // Use bounded join() to detect deadlock safely without freezing the application
        try {
            deadlockThread1.join(500);
            deadlockThread2.join(500);
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        if (deadlockThread1.isAlive() && deadlockThread2.isAlive()) {
            System.out.println("[DEADLOCK CONFIRMED] Thread 1 holds " + deadlockAccA.getAccountNumber() 
                    + " and waits for " + deadlockAccB.getAccountNumber() + ".");
            System.out.println("[DEADLOCK CONFIRMED] Thread 2 holds " + deadlockAccB.getAccountNumber() 
                    + " and waits for " + deadlockAccA.getAccountNumber() + ".");
            System.out.println("[DEADLOCK CONFIRMED] Circular wait reached! Both daemon threads are permanently blocked.");
            System.out.println("Application safely continues without freezing because bounded join() was used.");
        }

        // Part 4: Deadlock Prevention via Consistent Lock Ordering
        System.out.println("\n--- Step 4: Deadlock Prevention (Consistent Lock Ordering) ---");
        Account safeAccA = new SavingsAccount("SafeUserA", 1000, 0);
        Account safeAccB = new SavingsAccount("SafeUserB", 1000, 0);

        System.out.println("Safe Account A: " + safeAccA.getAccountNumber() + " (Balance: " + safeAccA.getBalance() + ")");
        System.out.println("Safe Account B: " + safeAccB.getAccountNumber() + " (Balance: " + safeAccB.getBalance() + ")");

        Thread safeThread1 = new Thread(() -> {
            try {
                safeAccA.transferDeadlockFree(safeAccB, 200);
                System.out.println("[SafeThread-1] Transferred 200 from " + safeAccA.getAccountNumber() 
                        + " to " + safeAccB.getAccountNumber());
            } catch (BankException e) {
                System.out.println("SafeThread-1 error: " + e.getMessage());
            }
        }, "SafeThread-1");

        Thread safeThread2 = new Thread(() -> {
            try {
                safeAccB.transferDeadlockFree(safeAccA, 100);
                System.out.println("[SafeThread-2] Transferred 100 from " + safeAccB.getAccountNumber() 
                        + " to " + safeAccA.getAccountNumber());
            } catch (BankException e) {
                System.out.println("SafeThread-2 error: " + e.getMessage());
            }
        }, "SafeThread-2");

        safeThread1.start();
        safeThread2.start();

        try {
            safeThread1.join();
            safeThread2.join();
        } catch (InterruptedException e) {
            System.out.println("Deadlock prevention test interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        System.out.println("Concurrent transfers completed successfully without deadlock!");
        System.out.println("Final Safe Account A Balance: " + safeAccA.getBalance() + " (Expected 900)");
        System.out.println("Final Safe Account B Balance: " + safeAccB.getBalance() + " (Expected 1100)");

        System.out.println("\n========== PRACTICAL 11 - SERIALIZATION & NIO PERSISTENCE ==========");

        Path dataFile = Paths.get("data", "accounts.dat");
        Path logsDir = Paths.get("data", "logs");
        Path logFile = logsDir.resolve("transactions.log");
        Path reportFile = Paths.get("data", "end_of_day_report.txt");

        try {
            // Step 1: Save state using StatePersister if data file does not exist (seed persistence)
            if (!java.nio.file.Files.exists(dataFile)) {
                System.out.println("Saving accounts array to: " + dataFile);
                StatePersister.save(accounts, dataFile);
                System.out.println("Accounts successfully saved.");
            } else {
                System.out.println("Existing persistence file found at: " + dataFile);
            }

            // Step 2: Load state using StatePersister
            System.out.println("\nReloading accounts array from: " + dataFile);
            Account[] reloadedAccounts = StatePersister.load(dataFile);
            System.out.println("Accounts loaded successfully. Count: " + reloadedAccounts.length);
            for (Account acc : reloadedAccounts) {
                System.out.println("  Reloaded: " + acc);
            }

            // Step 3: Append transactions using TransactionLog
            System.out.println("\nLogging transactions to: " + logFile);
            // Recreate or append
            TransactionLog.append(logFile, "DEPOSIT AC0001 500");
            TransactionLog.append(logFile, "WITHDRAW AC0002 200");
            TransactionLog.append(logFile, "DEPOSIT AC0003 1000");
            TransactionLog.append(logFile, "WITHDRAW AC0001 100");
            System.out.println("Transaction log entries recorded.");

            // Step 4: Generate NIO summary report
            System.out.println("\nGenerating End of Day Summary Report...");
            String reportText = ReportGenerator.generateReport(logsDir, reportFile);
            System.out.println(reportText);

        } catch (Exception e) {
            System.out.println("Practical 11 error: " + e.getMessage());
        }

        System.out.println("\n========== PRACTICAL 12 - COLLECTIONS FRAMEWORK ==========");

        AccountStore store = new AccountStore();

        Account storeAcc1 = new SavingsAccount("UserAlpha", 5000, 0);
        Account storeAcc2 = new SavingsAccount("UserBeta", 2000, 0);
        Account storeAcc3 = new SavingsAccount("UserGamma", 9000, 0);

        store.add(storeAcc1);
        store.add(storeAcc2);
        store.add(storeAcc3);
        store.logTransaction("Deposit 5000 to " + storeAcc1.getAccountNumber());
        store.logTransaction("Deposit 2000 to " + storeAcc2.getAccountNumber());
        store.logTransaction("Deposit 9000 to " + storeAcc3.getAccountNumber());

        // Step 1: Map look up by account number
        System.out.println("Lookup by account number (" + storeAcc2.getAccountNumber() + "):");
        Account fetched = store.get(storeAcc2.getAccountNumber());
        System.out.println("Found Account: " + fetched);

        // Step 2: Display Transaction History stored in ArrayList (Practical 12)
        System.out.println("\nTransaction History from AccountStore (stored in ArrayList):");
        List<String> history = store.getTransactionHistory();
        for (String entry : history) {
            System.out.println("  Log entry: " + entry);
        }
        System.out.println("History records count in ArrayList: " + history.size());

        // Step 3: Sorting with Comparator (descending by balance: 9000, 5000, 2000)
        System.out.println("\nAccounts sorted by balance (Comparator):");
        List<Account> sortedByBal = store.sortedByBalance();
        for (Account a : sortedByBal) {
            System.out.println("  " + a.getAccountNumber() + " - " + a.getOwnerName() + " : Balance = " + a.getBalance());
        }

        // Step 4: Natural ordering with Comparable<Account> (sorted by account number)
        System.out.println("\nAccounts sorted by account number (Comparable):");
        List<Account> naturalOrder = new ArrayList<>(store.getAll());
        Collections.sort(naturalOrder);
        for (Account a : naturalOrder) {
            System.out.println("  " + a.getAccountNumber() + " - " + a.getOwnerName());
        }

        // Step 5: ConcurrentHashMap in TransactionProcessor
        System.out.println("\nRegistering accounts in TransactionProcessor's ConcurrentHashMap:");
        TransactionProcessor concurrentProc = new TransactionProcessor();
        concurrentProc.registerAccount(storeAcc1);
        concurrentProc.registerAccount(storeAcc2);
        concurrentProc.registerAccount(storeAcc3);
        System.out.println("Registered shared accounts count: " + concurrentProc.getSharedAccounts().size());
        concurrentProc.stop();

        System.out.println("\n========== PRACTICAL 13 - GENERICS & REPOSITORY PATTERN ==========");

        // Step 1 & 2 & 3: Generic Repository<Account> with reflection on @Id and @SafeVarargs saveAll()
        Repository<Account> accountRepo = new Repository<>();

        Account repoAcc1 = new SavingsAccount("Devang", 3000, 500);
        Account repoAcc2 = new CurrentAccount("Pooja", 4000, 1000);
        Account repoAcc3 = new FixedDepositAccount("Karan", 15000);

        accountRepo.saveAll(repoAcc1, repoAcc2, repoAcc3);
        System.out.println("Saved 3 accounts to generic Repository<Account> using saveAll().");

        System.out.println("\nFetching account by id (" + repoAcc2.getAccountNumber() + "):");
        Account repoFetched = accountRepo.findById(repoAcc2.getAccountNumber());
        System.out.println("Fetched by id: " + repoFetched);

        System.out.println("\nPrinting all accounts in Repository<Account> (findAll):");
        for (Account a : accountRepo.findAll()) {
            System.out.println("  " + a);
        }

        // Reusing Repository<T> for Customer objects
        System.out.println("\nDemonstrating reusability with Repository<Customer>:");
        Repository<Customer> customerRepo = new Repository<>();
        customerRepo.save(c1);
        customerRepo.save(c2);
        Customer fetchedCust = customerRepo.findById(c1.getCustomerId());
        System.out.println("Fetched Customer by @Id: " + fetchedCust.getCustomerId() + " - " + fetchedCust.getName());

        // Step 4: Bounded type parameter generic method average()
        Double[] sampleBalances = {3000.0, 4000.0, 15000.0};
        double avgBal = Repository.average(sampleBalances);
        System.out.println("\nBounded Generic Method average() of [3000.0, 4000.0, 15000.0]: " + avgBal);

        // Step 5: Demonstrate Repository delete() method (Practical 13)
        System.out.println("\nDemonstrating Repository<Account>.delete():");
        System.out.println("Deleting demonstration account: " + repoAcc3.getAccountNumber());
        boolean isDeleted = accountRepo.delete(repoAcc3.getAccountNumber());
        System.out.println("delete(" + repoAcc3.getAccountNumber() + ") result: " + isDeleted);
        Account afterDelete = accountRepo.findById(repoAcc3.getAccountNumber());
        System.out.println("Verifying findById after delete: " + afterDelete + " (Expected: null)");
        System.out.println("Remaining accounts count in repository: " + accountRepo.findAll().size());

        System.out.println("\n========== PRACTICAL 14 - FINAL MINIBANK CAPSTONE INTEGRATION ==========");

        // Wire BankService with a dedicated repository and log directory
        Repository<Account> capstoneRepo = new Repository<>();
        BankService bankService = new BankService(capstoneRepo, logsDir);

        // Load saved accounts on start-up (Practical 11)
        if (java.nio.file.Files.exists(dataFile)) {
            bankService.loadSavedAccounts(dataFile);
        } else {
            // Seed initial sample accounts if no persistence file exists
            for (Account a : accounts) {
                capstoneRepo.save(a);
            }
        }

        // Process a safe batch of transactions using TransactionProcessor (Practical 10) over thread-safe store (Practical 12)
        // Uses dedicated temporary demonstration accounts so persistent customer balances are not modified (Priority 2)
        System.out.println("Processing safe batch transactions via TransactionProcessor over thread-safe store...");
        TransactionProcessor batchProcessor = new TransactionProcessor();
        Account batchAcc1 = new SavingsAccount("BatchDemoUser1", 1000, 0);
        Account batchAcc2 = new SavingsAccount("BatchDemoUser2", 1000, 0);

        batchProcessor.registerAccount(batchAcc1);
        batchProcessor.registerAccount(batchAcc2);

        for (int i = 1; i <= 4; i++) {
            final int batchId = i;
            batchProcessor.submit(() -> {
                try {
                    batchAcc1.deposit(250);
                    System.out.println("[CAPSTONE BATCH " + batchId + "] Deposited 250 to " 
                            + batchAcc1.getAccountNumber() + " by " + Thread.currentThread().getName());
                } catch (InvalidAmountException e) {
                    System.out.println("Batch deposit error: " + e.getMessage());
                }
            });
        }
        batchProcessor.stop();
        System.out.println("Capstone batch processing finished. " + batchAcc1.getAccountNumber() 
                + " Balance: " + batchAcc1.getBalance() + " (Expected: 2000)");

        Scanner sc = new Scanner(System.in);
        MenuOption option = null;

        do {
            System.out.println("\n========== MINI BANK MENU ==========");
            for (MenuOption opt : MenuOption.values()) {
                System.out.println(opt.getCode() + ". " + opt.getDescription());
            }
            System.out.print("Enter your choice: ");

            if (!sc.hasNextLine()) {
                break;
            }
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (1-7).");
                continue;
            }

            // Map integer choice explicitly to MenuOption enum constant (Priority 5)
            option = MenuOption.fromChoice(choice);
            if (option == null) {
                System.out.println("Invalid choice. Please enter a valid number (1-7).");
                continue;
            }

            switch (option) {
                case OPEN_ACCOUNT -> {
                    System.out.println("\n--- Open New Account ---");
                    System.out.print("Enter Account Type (1: Savings, 2: Current, 3: Fixed Deposit): ");
                    String type = sc.nextLine().trim();
                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Enter Customer Email: ");
                    String email = sc.nextLine().trim();
                    System.out.print("Enter Customer Mobile: ");
                    String mobile = sc.nextLine().trim();
                    System.out.print("Enter Opening Balance: ");
                    long openBal = 0;
                    try {
                        openBal = Long.parseLong(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid opening balance format.");
                        break;
                    }
                    long param = 0;
                    if ("1".equals(type) || type.toUpperCase().contains("SAVINGS")) {
                        System.out.print("Enter Minimum Balance requirement: ");
                        try {
                            param = Long.parseLong(sc.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid minimum balance format.");
                            break;
                        }
                    } else if ("2".equals(type) || type.toUpperCase().contains("CURRENT")) {
                        System.out.print("Enter Overdraft Limit: ");
                        try {
                            param = Long.parseLong(sc.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid overdraft limit format.");
                            break;
                        }
                    }
                    try {
                        Account newAcc = bankService.openAccount(type, name, email, mobile, openBal, param);
                        System.out.println("Success! Account created: " + newAcc);
                    } catch (Exception e) {
                        System.out.println("Error opening account: " + e.getMessage());
                    }
                }
                case DEPOSIT -> {
                    System.out.println("\n--- Deposit Funds ---");
                    System.out.print("Enter Account Number: ");
                    String accNum = sc.nextLine().trim();
                    System.out.print("Enter Amount to Deposit: ");
                    long amount = 0;
                    try {
                        amount = Long.parseLong(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount format.");
                        break;
                    }
                    try {
                        bankService.deposit(accNum, amount);
                        Account updated = capstoneRepo.findById(accNum);
                        System.out.println("Deposit successful! New balance: " + (updated != null ? updated.getBalance() : "N/A"));
                    } catch (BankException e) {
                        System.out.println("Deposit error: " + e.getMessage());
                    }
                }
                case WITHDRAW -> {
                    System.out.println("\n--- Withdraw Funds ---");
                    System.out.print("Enter Account Number: ");
                    String accNum = sc.nextLine().trim();
                    System.out.print("Enter Amount to Withdraw: ");
                    long amount = 0;
                    try {
                        amount = Long.parseLong(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount format.");
                        break;
                    }
                    try {
                        bankService.withdraw(accNum, amount);
                        Account updated = capstoneRepo.findById(accNum);
                        System.out.println("Withdrawal successful! New balance: " + (updated != null ? updated.getBalance() : "N/A"));
                    } catch (BankException e) {
                        System.out.println("Withdrawal error: " + e.getMessage());
                    }
                }
                case TRANSFER -> {
                    System.out.println("\n--- Transfer Funds ---");
                    System.out.print("Enter Source Account Number: ");
                    String fromAcc = sc.nextLine().trim();
                    System.out.print("Enter Destination Account Number: ");
                    String toAcc = sc.nextLine().trim();
                    System.out.print("Enter Transfer Amount: ");
                    long amount = 0;
                    try {
                        amount = Long.parseLong(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount format.");
                        break;
                    }
                    try {
                        bankService.transfer(fromAcc, toAcc, amount);
                        System.out.println("Transfer successful!");
                    } catch (BankException e) {
                        System.out.println("Transfer error: " + e.getMessage());
                    }
                }
                case EXIT -> {
                    System.out.println("\nSaving state and shutting down...");
                    bankService.persistAccounts(dataFile);
                    System.out.println("Thank you for using MiniBank!");
                }
                case STATEMENT -> {
                    System.out.println("\n--- Account Statement ---");
                    System.out.print("Enter Account Number: ");
                    String accNum = sc.nextLine().trim();
                    try {
                        System.out.println(bankService.statement(accNum));
                    } catch (BankException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case REPORT -> {
                    System.out.println("\n--- End of Day Summary Report ---");
                    System.out.println(bankService.generateReport(reportFile));
                }
            }

        } while (option != MenuOption.EXIT);

        sc.close();
    }
}

