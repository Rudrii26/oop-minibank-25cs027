
public class Driver {
    public static void main(String[] args) {

        Card[] cards = {
            new Card("King", "Hearts"),
            new Card("Queen", "Diamonds"),
            new Card("Ace", "Spades"),
            new Card("Jack", "Clubs"),
            new Card("Ace", "Spades")
        };

        int count = 0;
        boolean duplicateFound = false;

        // Add cards and check for duplicates
        for (int i = 0; i < cards.length; i++) {

            for (int j = 0; j < count; j++) {
                if (cards[i].equals(cards[j])) {
                    System.out.println("Duplicate found: " + cards[i]);
                    duplicateFound = true;
                    break;
                }
            }

            if (duplicateFound) {
                break;
            }

            count++;
        }

        if (!duplicateFound) {
            System.out.println("No duplicates found.");
        }
    }
}
