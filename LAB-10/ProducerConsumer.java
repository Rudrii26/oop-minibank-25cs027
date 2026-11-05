import java.util.LinkedList;
import java.util.Queue;

class Buffer {

    private Queue<Integer> queue = new LinkedList<>();
    private int capacity = 3;

    public synchronized void produce(int value) {

        while (queue.size() == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        queue.add(value);
        System.out.println("Produced: " + value);

        notify();
    }

    public synchronized void consume() {

        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        int value = queue.remove();
        System.out.println("Consumed: " + value);

        notify();
    }
}

class Producer implements Runnable {

    private Buffer buffer;

    Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 1; i <= 10; i++) {
            buffer.produce(i);
        }
    }
}

class Consumer implements Runnable {

    private Buffer buffer;

    Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 1; i <= 10; i++) {
            buffer.consume();
        }
    }
}

public class ProducerConsumer {

    public static void main(String[] args) {

        Buffer buffer = new Buffer();

        Thread producer = new Thread(new Producer(buffer));
        Thread consumer = new Thread(new Consumer(buffer));

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All items produced and consumed.");
    }
}