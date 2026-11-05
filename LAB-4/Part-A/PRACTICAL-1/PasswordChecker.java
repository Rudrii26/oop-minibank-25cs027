public class PasswordChecker {

    public static String strength(String pw) {
        int count = 0;

        // Check the four password rules
        boolean length = pw.length() >= 8;
        boolean upper = pw.matches(".*[A-Z].*");
        boolean digit = pw.matches(".*[0-9].*");
        boolean special = pw.matches(".*[^a-zA-Z0-9].*");

        if (length) {
            count++;
        }

        if (upper) {
            count++;
        }

        if (digit) {
            count++;
        }

        if (special) {
            count++;
        }

        System.out.println("Length at least 8: " + length);
        System.out.println("Contains uppercase: " + upper);
        System.out.println("Contains digit: " + digit);
        System.out.println("Contains special character: " + special);

        // Return the strength according to the number of rules passed
        if (count <= 1) {
            return "Weak";
        } else if (count <= 3) {
            return "Medium";
        } else {
            return "Strong";
        }
    }
}