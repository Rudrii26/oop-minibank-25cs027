class SeatBooking {
    int seatsLeft = 5;
    int booked = 0;

    void book() {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            seatsLeft--;
            booked++;
            System.out.println(Thread.currentThread().getName() + " booked a seat");
        }
    }

    synchronized void synchronizedBook() {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            seatsLeft--;
            booked++;
            System.out.println(Thread.currentThread().getName() + " booked a seat");
        }
    }
}

public class SeatBookingRace {
    static final int THREADS = 10;

    public static void main(String[] args) throws InterruptedException {

        SeatBooking booking = new SeatBooking();
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                booking.book();
            }, "Thread-" + (i + 1));

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\nWithout synchronization:");
        System.out.println("Seats left: " + booking.seatsLeft);
        System.out.println("Total booked: " + booking.booked);


        booking.seatsLeft = 5;
        booking.booked = 0;

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                booking.synchronizedBook();
            }, "Thread-" + (i + 1));

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\nWith synchronization:");
        System.out.println("Seats left: " + booking.seatsLeft);
        System.out.println("Total booked: " + booking.booked);
    }
}