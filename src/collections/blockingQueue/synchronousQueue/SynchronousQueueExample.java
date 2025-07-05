package collections.blockingQueue.synchronousQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {

    public static void main(String[] args) {
        // SynchronousQueue — це черга без буфера,
        // кожна операція put чекає, поки інша операція take відбудеться.
        // Використовується для прямої синхронізації між потоками (для синхронного handoff).
        BlockingQueue<Integer> queue = new SynchronousQueue<>();

        Thread producer = new Thread(() -> {
            try {
                int counter = 0;
                while (true) {
                    System.out.println("Producing: " + counter);
                    queue.put(counter); // чекатиме, доки не буде take
                    System.out.println("Produced: " + counter);
                    counter++;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Integer item = queue.take(); // чекатиме, доки не буде put
                    System.out.println("Consumed: " + item);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
