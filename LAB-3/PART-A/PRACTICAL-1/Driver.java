
public class Driver {
    public static void main(String[] args) {

        Point[] points = {
            new Point(2, 3),
            new Point(4, 5),
            new Point(2, 3),
            new Point(6, 7),
            new Point(4, 5)
        };

        int distinct = 0;

        // Check each point against the previous points
        for (int i = 0; i < points.length; i++) {
            boolean found = false;

            for (int j = 0; j < i; j++) {
                if (points[i].equals(points[j])) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                distinct++;
            }
        }

        System.out.println("Distinct: " + distinct);
    }
}
