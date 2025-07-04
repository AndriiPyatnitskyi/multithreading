package collections.cyclicBarier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable {

  CyclicBarrier cyclicBarrier;

  int id;

  public Worker(CyclicBarrier cyclicBarrier, int id) {
    this.cyclicBarrier = cyclicBarrier;
    this.id = id;
  }

  @Override
  public void run() {
    System.out.println("Working... id: " + id);
    try {
      Thread.sleep(1000);
      // await is waiting for each thread. then all thread broke barrier and "All tasks have been finished..." is printed
      cyclicBarrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      throw new RuntimeException(e);
    }

    System.out.println("After cyclicBarrier");
  }
}

public class Main {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    CyclicBarrier cyclicBarrier = new CyclicBarrier(5,
        () -> {
          System.out.println("All tasks have been finished...");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });

    for (int i = 0; i < 5; i++) {
      executorService.execute(new Worker(cyclicBarrier, i));
    }

  }
}
