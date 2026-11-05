class Counter {
    int count = 0;

    void increment() {
        count++;
    }

    synchronized void synchronizedIncrement() {
        count++;
    }
}

public class CounterRace {
    static final int THREADS = 10;
    static final int INCREMENTS = 100000;

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Without synchronization: " + counter.count);
        System.out.println("Expected: " + (THREADS * INCREMENTS));


        counter.count = 0;

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    counter.synchronizedIncrement();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("With synchronization: " + counter.count);
        System.out.println("Expected: " + (THREADS * INCREMENTS));
    }
}