
import java.util.Objects;

public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Returns card details in readable format
    public String toString() {
        return rank + " of " + suit;
    }

    // Checks whether two cards have the same rank and suit
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Card c = (Card) obj;

        return rank.equals(c.rank) && suit.equals(c.suit);
    }

    // Generates hash code using rank and suit
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
