package collections.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class FirstWorker implements Runnable {
  BlockingQueue<Integer> queue; 

  public FirstWorker(BlockingQueue<Integer> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    int counter = 0;

    while (true) {
      try {
        queue.put(counter);
//        System.out.println("Producing... counter: " + counter);
        counter++;
        Thread.sleep(100);
        System.out.println(queue);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

  }
}

class SecondWorker implements Runnable {
  BlockingQueue<Integer> queue;

  public SecondWorker(BlockingQueue<Integer> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Integer counter = queue.take();
        System.out.println("Consuming... counter: " + counter);
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

public class Main {

  public static void main(String[] args) {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    new Thread(new FirstWorker(queue)).start();
    new Thread(new SecondWorker(queue)).start();
  }
}
