package executors.scheduled_executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
ВАЖЛИВО:
- ScheduledExecutorService — розширення ExecutorService для планування задач.
- Метод scheduleAtFixedRate запускає задачу з початковою затримкою (1 секунда),
  а потім повторює її виконання з фіксованим інтервалом (кожну секунду).
- Виконання задачі відбувається у виділеному потоці з пулу.
- Відмінність від scheduleWithFixedDelay у тому, що scheduleAtFixedRate
  запускає наступне виконання за фіксованим графіком, незалежно від часу виконання задачі.
- Важливо пам'ятати, що ScheduledExecutorService також потрібно коректно завершувати
  за допомогою shutdown() (тут не показано для простоти).
*/

class Task implements Runnable {
    public void doWork() {
        System.out.println("Task is working... Thread name: " + Thread.currentThread().getName());
    }

    @Override
    public void run() {
        doWork();
    }
}

public class Main {
    public static void main(String[] args) {
        // Створюємо ScheduledExecutorService з одним потоком у пулі
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // Запускаємо задачу з початковою затримкою 1 секунда,
        // потім повторюємо кожну секунду (fixed rate)
        executorService.scheduleAtFixedRate(new Task(), 1, 1, TimeUnit.SECONDS);

        // Для реального додатку варто викликати shutdown() при завершенні,
        // щоб коректно звільнити ресурси.
    }
}
