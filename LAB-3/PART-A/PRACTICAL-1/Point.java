
import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Returns the point in readable format
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Checks whether two points have the same coordinates
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Point p = (Point) obj;
        return x == p.x && y == p.y;
    }

    // Generates hash code using x and y
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
