import java.util.Random;
import java.util.Scanner;

enum Move {
    ROCK, PAPER, SCISSORS, LIZARD, SPOCK
}

public class RPSLS {

    // Check which move wins
    static int winner(Move a, Move b) {
        if (a == b) {
            return 0;
        }

        return switch (a) {
            case ROCK -> (b == Move.SCISSORS || b == Move.LIZARD) ? 1 : -1;
            case PAPER -> (b == Move.ROCK || b == Move.SPOCK) ? 1 : -1;
            case SCISSORS -> (b == Move.PAPER || b == Move.LIZARD) ? 1 : -1;
            case LIZARD -> (b == Move.SPOCK || b == Move.PAPER) ? 1 : -1;
            case SPOCK -> (b == Move.SCISSORS || b == Move.ROCK) ? 1 : -1;
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0;
        int computerScore = 0;

        // Play five rounds
        for (int i = 1; i <= 5; i++) {
            System.out.println("\nRound " + i);
            System.out.print("Enter your move: ");

            Move player = Move.valueOf(sc.nextLine().toUpperCase());

            // Computer selects a random move
            Move computer = Move.values()[random.nextInt(5)];

            System.out.println("Your move: " + player);
            System.out.println("Computer move: " + computer);

            int result = winner(player, computer);

            if (result == 1) {
                System.out.println("You win this round");
                playerScore++;
            } else if (result == -1) {
                System.out.println("Computer wins this round");
                computerScore++;
            } else {
                System.out.println("This round is a tie");
            }
        }

        System.out.println("\nFinal Score: " + playerScore + "-" + computerScore);

        if (playerScore > computerScore) {
            System.out.println("You are the winner!");
        } else if (computerScore > playerScore) {
            System.out.println("Computer is the winner!");
        } else {
            System.out.println("The game is a tie!");
        }

        sc.close();
    }
}