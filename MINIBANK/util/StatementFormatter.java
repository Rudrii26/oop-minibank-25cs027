package util;

import model.Account;

// Practical 4: Multi-line account statement formatter using StringBuilder
public class StatementFormatter {

    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append("========== MINI BANK STATEMENT ==========\n");
        sb.append("Account Number: ").append(account.getAccountNumber()).append("\n");
        sb.append("Account Holder: ").append(account.getOwnerName()).append("\n");
        sb.append("Balance: ").append(account.getBalance()).append("\n");
        sb.append("Account Status: ").append(account.isActive() ? "Active" : "Inactive").append("\n");
        sb.append("=========================================");
        return sb.toString();
    }
}
