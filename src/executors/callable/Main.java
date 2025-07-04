package executors.callable;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/*
ВАЖЛИВО:
- Callable<T> — це інтерфейс, що дозволяє виконувати задачі, які повертають результат та можуть кидати винятки.
- ExecutorService управляє пулом потоків і приймає задачі на виконання.
- Метод submit(Callable) повертає Future<T> — об'єкт для отримання результату асинхронної задачі.
- Виклик future.get() блокує потік, доки задача не завершиться, і повертає результат.
- Не забувай викликати shutdown() у ExecutorService після завершення роботи, щоб коректно завершити пул потоків.
*/

class Processor implements Callable<String> {
    private final int id;

    public Processor(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        // Імітація роботи, наприклад, завантаження або обробки
        Thread.sleep(1000);
        return "Id: " + id + " Thread name: " + Thread.currentThread().getName();
    }
}

public class Main {
    public static void main(String[] args) {
        // Створюємо пул з двох потоків
        ExecutorService service = Executors.newFixedThreadPool(2);

        // Створюємо і запускаємо задачі, отримуємо список Future<String>
        List<Future<String>> list = IntStream.range(0, 9)
            .mapToObj(Processor::new)
            .map(service::submit)
            .toList();

        // Отримуємо і виводимо результати задач
        list.stream()
            .map(stringFuture -> {
                try {
                    return stringFuture.get(); // блокуючий виклик, чекає на завершення задачі
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            })
            .forEach(System.out::println);

        // Коректно завершуємо пул потоків
        service.shutdown();
    }
}
