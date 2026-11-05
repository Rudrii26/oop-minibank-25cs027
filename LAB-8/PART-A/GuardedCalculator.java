import java.util.Scanner;

class DivideByZeroException extends Exception {
    DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int attempt = 0;

        while (true) {
            attempt++;

            boolean success = false;

            try {
                System.out.print("Enter first number: ");
                double a = Double.parseDouble(sc.nextLine());

                System.out.print("Enter second number: ");
                double b = Double.parseDouble(sc.nextLine());

                System.out.print("Enter operator (+, -, *, /): ");
                String op = sc.nextLine();

                double result = 0;

                switch (op) {
                    case "+":
                        result = a + b;
                        break;

                    case "-":
                        result = a - b;
                        break;

                    case "*":
                        result = a * b;
                        break;

                    case "/":
                        if (b == 0) {
                            throw new DivideByZeroException("Cannot divide by zero");
                        }
                        result = a / b;
                        break;

                    default:
                        System.out.println("Invalid operator. Try again.");
                        continue;
                }

                System.out.println("Result: " + result);
                success = true;

            } catch (DivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (NumberFormatException e) {
                System.out.println("Invalid number input. Please enter numbers only.");

            } finally {
                System.out.println("Attempt " + attempt + " completed.");
                System.out.println();
            }

            if (success) {
                break;
            }
        }

        sc.close();
    }
}