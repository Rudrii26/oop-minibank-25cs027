package service;

import model.Command;
import model.TransactionType;

// Practical 4: Beginner-friendly command parser
public class CommandParser {

    public static Command parse(String line) {
        // 1. Check for blank or null input
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Command cannot be blank.");
        }

        // 2. Split on any whitespace (handles multiple spaces and tabs)
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format. Expected: <TYPE> <ACCOUNT_NUMBER> <AMOUNT>");
        }

        // 3. Parse and validate transaction type (DEPOSIT, WITHDRAW, TRANSFER)
        TransactionType type;
        try {
            type = TransactionType.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown command: '" + parts[0] + "'. Allowed: DEPOSIT, WITHDRAW, TRANSFER");
        }

        String accountNumber = parts[1];

        // 4. Parse and validate amount (must be a positive whole number)
        long amount;
        try {
            amount = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount: '" + parts[2] + "'. Must be a whole number.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero. Received: " + amount);
        }

        return new Command(type, accountNumber, amount);
    }
}
