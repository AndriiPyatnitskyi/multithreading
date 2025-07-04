package collections.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
ВАЖЛИВО:
- CountDownLatch використовується для очікування, поки певна кількість подій (потоків)
  виконаються, перш ніж продовжити.
- Лічильник встановлюється в конструкторі (тут 5).
- Кожен потік викликає countDown(), зменшуючи лічильник.
- Метод await() блокує потік, поки лічильник не досягне 0.
- Якщо в await() викликати з основного потоку — він буде чекати поки всі воркери завершать роботу.
- Якщо лічильник більший за кількість викликів countDown(), await() буде чекати вічно!
- В даному прикладі, ExecutorService створено з одним потоком, тому потоки будуть виконуватись послідовно.
*/

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
            Thread.sleep(2000);  // Імітація роботи
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        countDownLatch.countDown();  // Повідомляємо, що робота завершена
    }
}

public class Main {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        // Використано single-thread executor — потоки виконуються послідовно
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            executorService.execute(new Worker(countDownLatch, i));
        }

        try {
            countDownLatch.await(); // Чекаємо, поки всі 5 завдань виконаються
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All tasks have been finished");

        executorService.shutdown();
    }
}
