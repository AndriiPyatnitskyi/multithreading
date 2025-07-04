package collections.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
ВАЖЛИВО:
- BlockingQueue — це потоко-безпечна черга з вбудованою блокувальною логікою.
- Методи put() та take() блокують потік автоматично, якщо черга повна або порожня відповідно.
- Не треба писати synchronized, wait/notify, блокування — все робиться під капотом.
- Це значно спрощує реалізацію патерну Producer-Consumer.
- ArrayBlockingQueue — це черга з фіксованим розміром (capacity).

ЧОМУ BlockingQueue КРАЩЕ НІЖ wait/notify?
- Не потрібно явно керувати моніторами (synchronized), викликами wait/notify,
  що зменшує ризик помилок, таких як deadlock або пропущений notify.
- Потоки автоматично блокуються та прокидаються при додаванні/видаленні елементів.
- Код стає простішим для розуміння та підтримки.
- BlockingQueue — це офіційно рекомендований Java-підхід для патерну Producer-Consumer.
*/

class FirstWorker implements Runnable {
  private final BlockingQueue<Integer> queue;

  public FirstWorker(BlockingQueue<Integer> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
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
  }
}

class SecondWorker implements Runnable {
  private final BlockingQueue<Integer> queue;

  public SecondWorker(BlockingQueue<Integer> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
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
  }
}

public class Main {

  public static void main(String[] args) {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    new Thread(new FirstWorker(queue)).start();
    new Thread(new SecondWorker(queue)).start();
  }
}
