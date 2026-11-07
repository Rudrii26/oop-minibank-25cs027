
import java.util.Objects;

public class Account_Backup {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account_Backup(String ownerName, long openingBalance) {
        this(generateAccountNumber(), ownerName, openingBalance);
    }

    public Account_Backup(String ownerName) {
        this(ownerName, 0);
    }

    Account_Backup(String accountNumber, String ownerName, long openingBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = openingBalance >= 0 ? openingBalance : 0;
        this.active = true;
    }

    public void deposit(long amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Account_Backup)) {
            return false;
        }

        Account_Backup account = (Account_Backup) o;

        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
