package atomicInteger;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(Main::increment);
        Thread t2 = new Thread(Main::increment);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter);
    }


//    private static int counter = 0;
//
//    private synchronized static void increment() {
//        for (int i = 0; i < 10_000; i++) {
//            counter++;
//        }
//    }


    // Instead of using int counter and use synchronized method, we can use AtomicInteger
    private static final AtomicInteger counter = new AtomicInteger(0);

        private static void increment() {
        for (int i = 0; i < 10_000; i++) {
            counter.incrementAndGet();
        }
    }
}
