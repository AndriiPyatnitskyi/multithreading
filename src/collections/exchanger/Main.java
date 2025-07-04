package collections.exchanger;

import static java.util.concurrent.CompletableFuture.runAsync;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;

/*
ВАЖЛИВО:
- Exchanger<T> — синхронізує дві ниті для обміну об'єктами типу T.
- Коли одна нитка викликає exchange(), вона блокується, поки друга нитка
  також не викликає exchange(). Тоді обидві ниті обмінюються об'єктами.
- Дуже корисний для задач, де два потоки працюють парно та мають обмінюватись даними.
*/

public class Main {
  public static void main(String[] args) {
    Exchanger<String> exchanger = new Exchanger<>();

    Runnable taskA = () -> {
      try {
        // Віддаємо "from A", чекаємо отримати повідомлення від taskB
        String message = exchanger.exchange("from A");
        System.out.println("taskA received: " + message);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    Runnable taskB = () -> {
      try {
        // Віддаємо "from B", чекаємо отримати повідомлення від taskA
        String message = exchanger.exchange("from B");
        System.out.println("taskB received: " + message);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    // Запускаємо обидва таски асинхронно і чекаємо завершення обох
    CompletableFuture.allOf(
        runAsync(taskA), runAsync(taskB)).join();
  }
}
