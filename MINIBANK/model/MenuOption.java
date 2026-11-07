package model;

// Practical 1 & 14: Enum representing the complete interactive banking menu choices
public enum MenuOption {
    OPEN_ACCOUNT(1, "Open Account"),
    DEPOSIT(2, "Deposit"),
    WITHDRAW(3, "Withdraw"),
    TRANSFER(4, "Transfer"),
    EXIT(5, "Exit"),
    STATEMENT(6, "Account Statement"),
    REPORT(7, "End-of-Day Audit Report");

    private final int code;
    private final String description;

    MenuOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // Static helper to map an integer choice safely to the corresponding MenuOption enum (Priority 5)
    public static MenuOption fromChoice(int choice) {
        for (MenuOption option : values()) {
            if (option.code == choice) {
                return option;
            }
        }
        return null;
    }
}
