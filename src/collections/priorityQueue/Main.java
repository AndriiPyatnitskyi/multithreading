package collections.priorityQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/*
ВАЖЛИВО:
- PriorityBlockingQueue — це неблокуюча потокобезпечна черга з пріоритетом.
- Елементи дістаються з черги у відповідності до їх природного порядку
  (Comparable) або за допомогою Comparator, якщо він заданий.
- У цьому прикладі — це Integer, тож елементи будуть видаватись за зростанням.
- Метод put() додає елемент у чергу, take() дістає і видаляє найбільш пріоритетний елемент,
  блокуючись, якщо черга порожня.
- Важливо: черга не гарантує порядок вставки, лише порядок пріоритету.
*/

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
        // Елементи виводяться у порядку зростання: 2, 5, 10, 100
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
