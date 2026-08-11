import java.util.Scanner;

public class CinemaShow {

    private String title;
    private int seatsAvailable;
    private final int capacity;

    private static int totalBooked = 0;

    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    public CinemaShow(String title) {
        this(title, 100);
    }

    public boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    public void cancel(int n) {
        seatsAvailable += n;

        if (seatsAvailable > capacity)
            seatsAvailable = capacity;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie title: ");
        String title = sc.nextLine();

        System.out.print("Enter capacity: ");
        int capacity = sc.nextInt();

        CinemaShow show = new CinemaShow(title, capacity);

        System.out.print("\nEnter seats to book: ");
        int book1 = sc.nextInt();

        System.out.println("Booking Successful: " + show.book(book1));
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.print("\nEnter seats to cancel: ");
        int cancel = sc.nextInt();

        show.cancel(cancel);
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.print("\nEnter seats to book again: ");
        int book2 = sc.nextInt();

        System.out.println("Booking Successful: " + show.book(book2));
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.println("\nTotal Booked Seats: " + CinemaShow.getTotalBooked());

        sc.close();
    }
}