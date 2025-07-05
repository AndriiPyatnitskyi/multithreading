package collections.blockingQueue.linkedBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueExample {

    public static void main(String[] args) {
        // LinkedBlockingQueue — це блокувальна черга з необмеженим або обмеженим розміром.
        // Часто використовується в producer-consumer задачах завдяки високій пропускній здатності.
        // Зазвичай краще підходить для producer-consumer ніж ArrayBlockingQueue через кращу пропускну здатність.
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

        Thread producer = new Thread(() -> {
            try {
                int counter = 0;
                while (true) {
                    queue.put(counter);
                    System.out.println("Produced: " + counter);
                    counter++;
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Integer item = queue.take();
                    System.out.println("Consumed: " + item);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
