import java.util.Scanner;

enum Coin {
    ONE, TWO, FIVE, TEN
}

public class VendingMachine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int price = 15;
        int total = 0;

        // Keep accepting coins until enough money is collected
        while (total < price) {
            System.out.print("Enter coin (ONE, TWO, FIVE, TEN): ");
            String input = sc.nextLine().toUpperCase();

            Coin coin = Coin.valueOf(input);

            // Convert the coin name into its value
            int value = switch (coin) {
                case ONE -> 1;
                case TWO -> 2;
                case FIVE -> 5;
                case TEN -> 10;
            };

            total = total + value;
            System.out.println("Total so far: " + total);
        }

        // Calculate the remaining change
        System.out.println("Paid. Change: " + (total - price));

        sc.close();
    }
}