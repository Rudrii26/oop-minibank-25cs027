public class CinemaShow {
    private String movieName;
    private int seatsAvailable;
    final int capacity;

    static int totalBooked = 0;

    // Constructor
    CinemaShow(String movieName, int capacity) {
        this.movieName = movieName;
        this.capacity = capacity;
        seatsAvailable = capacity;
    }

    // Book seats if enough seats are available
    void bookSeats(int seats) {
        if (seats <= 0) {
            System.out.println("Invalid number of seats");
        } else if (seats <= seatsAvailable) {
            seatsAvailable = seatsAvailable - seats;
            totalBooked = totalBooked + seats;
            System.out.println(seats + " seats booked successfully");
        } else {
            System.out.println("Not enough seats available");
        }
    }

    // Cancel previously booked seats
    void cancelSeats(int seats) {
        if (seats <= 0 || seats > capacity - seatsAvailable) {
            System.out.println("Invalid cancellation");
        } else {
            seatsAvailable = seatsAvailable + seats;
            totalBooked = totalBooked - seats;
            System.out.println(seats + " seats cancelled");
        }
    }

    int getSeatsAvailable() {
        return seatsAvailable;
    }

    String getMovieName() {
        return movieName;
    }

    public static void main(String[] args) {
        CinemaShow show = new CinemaShow("Avengers", 50);

        System.out.println("Movie: " + show.getMovieName());
        System.out.println("Available seats: " + show.getSeatsAvailable());

        show.bookSeats(20);
        show.bookSeats(25);
        show.bookSeats(10);

        show.cancelSeats(5);

        System.out.println("Available seats: " + show.getSeatsAvailable());
        System.out.println("Total booked seats: " + totalBooked);
    }
}