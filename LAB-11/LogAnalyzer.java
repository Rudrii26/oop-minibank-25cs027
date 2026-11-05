import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class LogAnalyzer {
    public static void main(String[] args) {

        String[] fileNames = {"log1.txt", "log2.txt", "log3.txt"};
        String keyword = "ERROR";

        int totalLines = 0;
        int matchingLines = 0;

        try {
            Files.write(Path.of("log1.txt"), Arrays.asList(
                "Application started",
                "ERROR: Connection failed",
                "User logged in"
            ));

            Files.write(Path.of("log2.txt"), Arrays.asList(
                "ERROR: File not found",
                "Application running",
                "User logged out"
            ));

            Files.write(Path.of("log3.txt"), Arrays.asList(
                "Database connected",
                "ERROR: Database timeout",
                "Application stopped"
            ));

            for (String fileName : fileNames) {
                Path path = Path.of(fileName);

                System.out.println("\nFile: " + fileName);

                BasicFileAttributes attributes =
                    Files.readAttributes(path, BasicFileAttributes.class);

                System.out.println("File size: " + attributes.size() + " bytes");

                List<String> lines = Files.readAllLines(path);

                for (String line : lines) {
                    totalLines++;

                    if (line.contains(keyword)) {
                        matchingLines++;
                    }
                }
            }

            System.out.println("\nTotal lines: " + totalLines);
            System.out.println("Lines containing " + keyword + ": " + matchingLines);

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}