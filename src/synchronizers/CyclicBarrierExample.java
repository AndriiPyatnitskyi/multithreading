package synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
ВАЖЛИВО:
- CyclicBarrier дозволяє групі потоків очікувати один одного до певного моменту.
- Потоки викликають метод await() і блокуються, поки всі задані потоки не викличуть await().
- Коли всі потоки дійдуть до барʼєру, виконуватиметься додаткове дію (Runnable),
  передане в конструктор CyclicBarrier.
- Барʼєр є "циклічним" — його можна використовувати повторно після того,
  як він розблокував всі потоки.
- BrokenBarrierException кидається, якщо барʼєр був порушений (наприклад,
  якщо один із потоків був перерваний).
*/

class CyclicBarrierWorker implements Runnable {

  CyclicBarrier cyclicBarrier;
  int id;

  public CyclicBarrierWorker(CyclicBarrier cyclicBarrier, int id) {
    this.cyclicBarrier = cyclicBarrier;
    this.id = id;
  }

  @Override
  public void run() {
    System.out.println("Working... id: " + id);
    try {
      Thread.sleep(1000); // Імітація роботи

      // Потік очікує, поки всі 5 потоків не викличуть await()
      cyclicBarrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      throw new RuntimeException(e);
    }

    // Цей код виконається після того, як всі потоки дійдуть до барʼєру
    System.out.println("After cyclicBarrier");
  }
}

public class CyclicBarrierExample {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    // Барʼєр на 5 потоків, після розблокування виконається лямбда
    CyclicBarrier cyclicBarrier = new CyclicBarrier(5,
        () -> {
          System.out.println("All tasks have been finished...");
          try {
            Thread.sleep(1000); // Імітація деякої операції після барʼєру
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });

    // Запускаємо 5 потоків, які працюють і чекають на барʼєрі
    for (int i = 0; i < 5; i++) {
      executorService.execute(new CyclicBarrierWorker(cyclicBarrier, i));
    }

    executorService.shutdown();
  }
}
