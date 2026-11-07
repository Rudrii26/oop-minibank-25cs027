package service;

import model.Account;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

// Practical 11: End-of-day report generator using Java NIO DirectoryStream and BufferedReader
public class ReportGenerator {

    // Overload taking total bank balance across accounts (Priority 3)
    public static String generateReport(Path logsDir, Path reportFile, long totalBankBalance) throws IOException {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        long processedFiles = 0;

        StringBuilder report = new StringBuilder();
        report.append("========== END OF DAY TRANSACTION REPORT ==========\n");

        if (Files.exists(logsDir) && Files.isDirectory(logsDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        processedFiles++;
                        BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                        report.append(String.format("Log File: %s | Size: %d bytes | Modified: %s\n",
                                entry.getFileName(), attrs.size(), attrs.lastModifiedTime()));

                        try (BufferedReader reader = Files.newBufferedReader(entry, StandardCharsets.UTF_8)) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                line = line.trim();
                                if (line.isEmpty()) continue;

                                String[] parts = line.split("\\s+");
                                if (parts.length >= 3) {
                                    String type = parts[0].toUpperCase();
                                    try {
                                        long amount = Long.parseLong(parts[parts.length - 1]);
                                        if ("DEPOSIT".equals(type)) {
                                            totalDeposits += amount;
                                        } else if ("WITHDRAW".equals(type)) {
                                            totalWithdrawals += amount;
                                        }
                                    } catch (NumberFormatException ignored) {}
                                }
                            }
                        }
                    }
                }
            }
        }

        long netChange = totalDeposits - totalWithdrawals;

        report.append("---------------------------------------------------\n");
        report.append("Total Files Processed: ").append(processedFiles).append("\n");
        report.append("Total Deposits: ").append(totalDeposits).append("\n");
        report.append("Total Withdrawals: ").append(totalWithdrawals).append("\n");
        report.append("Net Change: ").append(netChange).append("\n");
        if (totalBankBalance >= 0) {
            report.append("Total Balance across all accounts: ").append(totalBankBalance).append("\n");
        }
        report.append("===================================================\n");

        if (reportFile.getParent() != null) {
            Files.createDirectories(reportFile.getParent());
        }
        Files.writeString(reportFile, report.toString(), StandardCharsets.UTF_8);

        return report.toString();
    }

    // Default method calculating total balance from data/accounts.dat if available
    public static String generateReport(Path logsDir, Path reportFile) throws IOException {
        long totalBal = -1;
        Path dataFile = logsDir.resolveSibling("accounts.dat");
        if (Files.exists(dataFile)) {
            try {
                Account[] loaded = StatePersister.load(dataFile);
                if (loaded != null) {
                    totalBal = 0;
                    for (Account a : loaded) {
                        totalBal += a.getBalance();
                    }
                }
            } catch (Exception ignored) {}
        }
        return generateReport(logsDir, reportFile, totalBal);
    }
}
