package service;

// Practical 10: Producer-Consumer pattern using a shared bounded circular buffer.
// Demonstrates thread coordination with synchronized, wait(), and notifyAll().
public class TransactionBuffer {

    private final String[] buffer;
    private final int capacity;
    private int count = 0;
    private int in = 0;
    private int out = 0;

    public TransactionBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new String[capacity];
    }

    // Producer method: inserts a transaction into the buffer
    public synchronized void produce(String transaction) throws InterruptedException {
        // We MUST use a 'while' loop instead of an 'if' statement:
        // 1. Spurious wakeups: A thread can wake up without any call to notify().
        // 2. Condition check: When multiple producers wake up, another producer might
        //    have filled the buffer first. The while loop re-checks the condition.
        while (count == capacity) {
            wait(); // Releases the monitor lock and waits for a consumer to remove an item
        }

        buffer[in] = transaction;
        in = (in + 1) % capacity;
        count++;
        System.out.println("[PRODUCER] Added: " + transaction + " (Buffer count: " + count + ")");

        // Notify waiting consumer threads that an item is available
        notifyAll();
    }

    // Consumer method: removes and processes a transaction from the buffer
    public synchronized String consume() throws InterruptedException {
        // While loop ensures the buffer is not empty before attempting to consume
        while (count == 0) {
            wait(); // Releases the lock and waits for a producer to add an item
        }

        String transaction = buffer[out];
        out = (out + 1) % capacity;
        count--;
        System.out.println("[CONSUMER] Processed: " + transaction + " (Buffer count: " + count + ")");

        // Notify waiting producer threads that space is now available
        notifyAll();
        return transaction;
    }
}
