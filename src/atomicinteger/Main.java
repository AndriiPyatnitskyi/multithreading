package atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(Main::increment);
        Thread t2 = new Thread(Main::increment);

        t1.start();
        t2.start();

        // Чекаємо завершення потоків
        t1.join();
        t2.join();

        // Виводимо значення лічильника
        System.out.println(counter);
    }

    /*
    // Варіант із synchronized та простим int (коментовано)
    private static int counter = 0;

    private synchronized static void increment() {
        for (int i = 0; i < 10_000; i++) {
            counter++;
        }
    }
    */

    /**
     * AtomicInteger - це клас із пакету java.util.concurrent.atomic,
     * який забезпечує атомарні операції над цілим числом без блокувань (lock-free).
     *
     * Переваги AtomicInteger над synchronized:
     * - Вищий рівень продуктивності в багатопотокових середовищах.
     * - Запобігає втратам при одночасному доступі (race conditions).
     *
     * Метод incrementAndGet():
     * - Атомарно збільшує значення на 1.
     * - Повертає оновлене значення.
     *
     * Застосовується для лічильників і простих змінних, де потрібна атомарність.
     * Для складніших операцій потрібна синхронізація або Lock.
     */
    private static final AtomicInteger counter = new AtomicInteger(0);

    private static void increment() {
        for (int i = 0; i < 10_000; i++) {
            counter.incrementAndGet();
        }
    }
}
