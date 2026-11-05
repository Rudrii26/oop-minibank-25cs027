public class Driver {
    public static void main(String[] args) {
        String[] passwords = {
            "abc",
            "Abcd1234!",
            "hello123",
            "HELLO@123"
        };

        // Check every password in the array
        for (String pw : passwords) {
            System.out.println("\nPassword: " + pw);
            System.out.println("Strength: " + PasswordChecker.strength(pw));
        }
    }
}