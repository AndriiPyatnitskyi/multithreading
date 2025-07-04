package collections.priorityQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class FirstWorker implements Runnable {
  BlockingQueue<Integer> queue; 

  public FirstWorker(BlockingQueue<Integer> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      queue.put(100);
      queue.put(10);
      queue.put(2);
      queue.put(5);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
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
        // should print elements in order to comparable results
        System.out.println("Consuming... counter: " + queue.take());
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

public class Main {

  public static void main(String[] args) {
    BlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

    new Thread(new FirstWorker(queue)).start();
    new Thread(new SecondWorker(queue)).start();
  }
}
