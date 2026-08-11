
public class PasswordChecker {

    // Length >= 8
    public static boolean hasMinLength(String pw) {
        return pw.length() >= 8;
    }

    // Contains uppercase letter
    public static boolean hasUpperCase(String pw) {
        return pw.matches(".*[A-Z].*");
    }

    // Contains digit
    public static boolean hasDigit(String pw) {
        return pw.matches(".*[0-9].*");
    }

    // Contains special character
    public static boolean hasSpecialCharacter(String pw) {
        return pw.matches(".*[^a-zA-Z0-9].*");
    }

    // Weak, Medium, or Strong
    public static String strength(String pw) {
        int count = 0;

        if (hasMinLength(pw))
            count++;
        if (hasUpperCase(pw))
            count++;
        if (hasDigit(pw))
            count++;
        if (hasSpecialCharacter(pw))
            count++;

        if (count <= 1)
            return "Weak";
        else if (count <= 3)
            return "Medium";
        else
            return "Strong";
    }
}