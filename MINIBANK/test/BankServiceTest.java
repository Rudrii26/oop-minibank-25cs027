package test;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.Account;
import model.CurrentAccount;
import model.SavingsAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.AccountWorker;
import service.BankService;
import service.Repository;
import service.StatePersister;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class BankServiceTest {

    @Test
    @DisplayName("Requirement 4.1: Deposit increases account balance")
    public void testDepositIncreasesBalance() throws Exception {
        Repository<Account> repo = new Repository<>();
        BankService service = new BankService(repo, Paths.get("data", "test_logs"));

        Account acc = service.openAccount("SAVINGS", "TestUser1", "test1@gmail.com", "9876543210", 1000, 500);
        long initialBalance = acc.getBalance();
        service.deposit(acc.getAccountNumber(), 500);

        assertEquals(initialBalance + 500, acc.getBalance(), "Deposit should increase the balance by exactly 500.");
    }

    @Test
    @DisplayName("Requirement 4.2: Withdrawal over allowed limit throws InsufficientFundsException")
    public void testWithdrawalOverLimitThrowsException() throws Exception {
        Repository<Account> repo = new Repository<>();
        BankService service = new BankService(repo, Paths.get("data", "test_logs"));

        Account acc = service.openAccount("SAVINGS", "TestUser2", "test2@gmail.com", "9876543211", 1000, 500);

        // Test 1: Withdrawing beyond available balance (1500 > 1000)
        assertThrows(
                InsufficientFundsException.class,
                () -> service.withdraw(acc.getAccountNumber(), 1500),
                "Expected InsufficientFundsException when withdrawing beyond balance."
        );

        // Test 2: Withdrawing below minimum balance requirement (leaves 200, below minBalance 500)
        assertThrows(
                InsufficientFundsException.class,
                () -> service.withdraw(acc.getAccountNumber(), 800),
                "Expected InsufficientFundsException when withdrawing below minimum balance."
        );
    }

    @Test
    @DisplayName("Requirement 4.3: Concurrent deposits maintain exact balance")
    public void testConcurrentDepositsKeepBalanceCorrect() throws Exception {
        Account acc = new SavingsAccount("ConcurrentTestUser", 0, 0);

        int threads = 10;
        int depositsPerThread = 1000;
        Thread[] workerThreads = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            workerThreads[i] = new Thread(new AccountWorker(acc, depositsPerThread, 1, true));
            workerThreads[i].start();
        }

        for (Thread t : workerThreads) {
            t.join();
        }

        assertEquals(10000, acc.getBalance(), "Concurrent deposits should maintain exact final balance of 10000.");
    }

    @Test
    @DisplayName("Requirement 4.4: Successful transfer updates sender and receiver balances")
    public void testSuccessfulTransferUpdatesBalances() throws Exception {
        Repository<Account> repo = new Repository<>();
        BankService service = new BankService(repo, Paths.get("data", "test_logs"));

        Account sender = service.openAccount("SAVINGS", "TransferSender", "sender@gmail.com", "9876543212", 2000, 500);
        Account receiver = service.openAccount("SAVINGS", "TransferReceiver", "recv@gmail.com", "9876543213", 1000, 500);

        service.transfer(sender.getAccountNumber(), receiver.getAccountNumber(), 600);

        assertEquals(1400, sender.getBalance(), "Sender balance should be deducted by exactly 600.");
        assertEquals(1600, receiver.getBalance(), "Receiver balance should be credited with exactly 600.");
    }

    @Test
    @DisplayName("Requirement 4.5: Transaction rollback refunds sender if receiver fails")
    public void testTransferRollbackOnDestinationFailure() throws Exception {
        Repository<Account> repo = new Repository<>();
        BankService service = new BankService(repo, Paths.get("data", "test_logs"));

        Account sender = service.openAccount("SAVINGS", "RollbackUser", "rollback@gmail.com", "9876543214", 2500, 500);

        // Subclass simulating a destination account that fails deposit
        Account failingTarget = new SavingsAccount("FailingTarget", 500, 0) {
            @Override
            public synchronized void deposit(long amount) throws InvalidAmountException {
                throw new InvalidAmountException("Receiver account cannot accept incoming deposits.");
            }
        };
        repo.save(failingTarget);

        assertThrows(BankException.class, () -> {
            service.transfer(sender.getAccountNumber(), failingTarget.getAccountNumber(), 700);
        }, "Expected transfer to fail due to receiver exception.");

        assertEquals(2500, sender.getBalance(), "Sender balance must be rolled back to the original 2500.");
    }

    @Test
    @DisplayName("Requirement 4.6: StatePersister correctly saves and reloads accounts")
    public void testStatePersisterSavesAndReloads() throws Exception {
        Path tempFile = Paths.get("data", "test_persist_accounts.dat");
        try {
            Account[] initial = new Account[]{
                    new SavingsAccount("PersistUser1", 3000, 500),
                    new CurrentAccount("PersistUser2", 4000, 1000)
            };

            StatePersister.save(initial, tempFile);
            assertTrue(Files.exists(tempFile), "Persistence file must exist on disk.");

            Account[] reloaded = StatePersister.load(tempFile);
            assertNotNull(reloaded, "Reloaded accounts array should not be null.");
            assertEquals(2, reloaded.length, "Must reload exactly 2 accounts.");
            assertEquals(initial[0].getAccountNumber(), reloaded[0].getAccountNumber(), "Account number must match.");
            assertEquals(initial[0].getBalance(), reloaded[0].getBalance(), "Balance must match.");
            assertEquals(initial[1].getAccountNumber(), reloaded[1].getAccountNumber(), "Account number must match.");
            assertEquals(initial[1].getBalance(), reloaded[1].getBalance(), "Balance must match.");
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    public static void main(String[] args) {
        System.out.println("Executing JUnit 5 tests via ConsoleLauncher...");
        org.junit.platform.console.ConsoleLauncher.main(new String[]{"--select-class", "test.BankServiceTest"});
    }
}
