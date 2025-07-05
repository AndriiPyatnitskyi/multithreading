package collections.blockingQueue.priorityBlockingQueue;

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

public class PriorityBlockingQueueExample {

  public static void main(String[] args) {
    BlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

    new Thread(() -> {
      try {
        queue.put(100);
        queue.put(10);
        queue.put(2);
        queue.put(5);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }).start();

    new Thread(() -> {
      while (true) {
        try {
          // Елементи виводяться у порядку зростання: 2, 5, 10, 100
          System.out.println("Consuming... counter: " + queue.take());
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();
  }
}
