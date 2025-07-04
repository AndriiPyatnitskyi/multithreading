package executors.fixed_thread_pool_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
ВАЖЛИВО:
- ExecutorService — це високорівневий інтерфейс для управління пулом потоків.
- Executors.newFixedThreadPool(2) створює пул з фіксованою кількістю потоків (2).
- Метод execute(Runnable) ставить задачу в чергу на виконання одним із потоків пулу.
- Якщо всі потоки зайняті, задачі чекають в черзі, доки потік не звільниться.
- Важливо викликати shutdown(), щоб коректно завершити роботу ExecutorService
  і звільнити ресурси.
*/

class Task implements Runnable {
    private final int id;

    public Task(int id) {
        this.id = id;
    }

    public void doWork() {
        System.out.println("Task with id: " + id + " is working... Thread name: " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // Імітація роботи (1 секунда)
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        doWork();
    }
}

public class Main {
    public static void main(String[] args) {
        // Створюємо пул з двох потоків
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Надсилаємо 5 задач на виконання
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task(i));
        }

        // Коректно завершуємо ExecutorService після виконання задач
        executorService.shutdown();
    }
}
