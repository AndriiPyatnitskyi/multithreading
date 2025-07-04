package producer_consumer;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

class Processor {
    private final List<Integer> list = new ArrayList<>();
    private static final Integer UPPER_LIMIT = 5;
    private static final Integer LOWER_LIMIT = 0;
    private final Object lock = new Object();
    private int value = 0;

    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Adding to list is done...");
                    value = 0;
                    lock.wait();
                } else {
                    System.out.println("Adding to list: " + value);
                    list.add(value);
                    value++;
                    lock.notify();
                }
                Thread.sleep(500);

            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Removing from list is done...");
                    lock.wait();
                } else {
                    System.out.println("Removing from list: " + list.remove(list.size() - 1));
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
    }

}
