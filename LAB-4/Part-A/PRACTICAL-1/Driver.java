public class Driver {
    public static void main(String[] args) {

        String[] passwords = {
                "abc",
                "password",
                "Password",
                "Password1",
                "Abcd1234!"
        };

        for (String pw : passwords) {
            System.out.println("Password: " + pw);

            System.out.println("Length >= 8       : " + PasswordChecker.hasMinLength(pw));
            System.out.println("Uppercase Letter  : " + PasswordChecker.hasUpperCase(pw));
            System.out.println("Contains Digit    : " + PasswordChecker.hasDigit(pw));
            System.out.println("Special Character : " + PasswordChecker.hasSpecialCharacter(pw));

            System.out.println("Strength: " + PasswordChecker.strength(pw));
            System.out.println("-------------------------------");
        }
    }
}