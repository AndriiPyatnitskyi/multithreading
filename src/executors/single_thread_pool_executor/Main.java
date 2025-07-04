package executors.single_thread_pool_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
ВАЖЛИВО:
- Executors.newSingleThreadExecutor() створює пул, який містить лише один потік.
- Усі задачі виконуються послідовно, в порядку подання у чергу.
- Якщо потік завершиться через помилку, Executor створить новий для подальшого виконання.
- Використовується, коли потрібно гарантувати послідовне виконання задач в одному потоці.
- Як і у випадку з іншими ExecutorService, важливо викликати shutdown() після завершення роботи.
*/

class Task implements Runnable {
    private int id;

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
        // Створюємо ExecutorService з одним потоком
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Надсилаємо 5 задач, які будуть виконуватись послідовно
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task(i));
        }

        // Коректно завершуємо ExecutorService
        executorService.shutdown();
    }
}
