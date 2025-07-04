package collections.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable{
    CountDownLatch countDownLatch;

    int id;

    public Worker(CountDownLatch countDownLatch, int id) {
        this.countDownLatch = countDownLatch;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Working... id: " + id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        countDownLatch.countDown();
    }
}

public class Main {

    public static void main(String[] args) {
        // if count in constructor more than threads count then main thread will freeze after all threads finished.
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            executorService.execute(new Worker(countDownLatch, i));
        }

        try {
            //  await similar to join() in Thread.
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All tasks have been finished");
        executorService.shutdown();


    }
}
