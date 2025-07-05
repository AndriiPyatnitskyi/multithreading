package collections.blockingQueue.arrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
ArrayBlockingQueue — це реалізація BlockingQueue з фіксованим розміром (capacity).
Вона використовує масив як внутрішню структуру даних і гарантує порядок FIFO.
Ідеально підходить для обмежених за розміром буферів.
*/

public class ArrayBlockingQueueExample {

  public static void main(String[] args) {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    Thread producer = new Thread(() -> {
      int counter = 0;
      while (true) {
        try {
          // Якщо черга заповнена, потік тут чекає звільнення місця
          queue.put(counter);

          // Імітація роботи
          Thread.sleep(100);

          System.out.println("Producing... counter: " + counter);
          counter++;
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

    Thread consumer = new Thread(() -> {
      while (true) {
        try {
          // Якщо черга порожня, потік чекає на нові елементи
          Integer counter = queue.take();

          System.out.println("Consuming... counter: " + counter);

          // Імітація повільнішої обробки
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

    producer.start();
    consumer.start();
  }
}
