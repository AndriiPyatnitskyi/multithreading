package memoryvisibility;

/**
 * Цей приклад демонструє, чому важливо використовувати ключове слово `volatile`
 * при роботі з міжпотоковими флагами зупинки (termination flags).
 */
class Worker implements Runnable {

    /**
     * Ключове слово `volatile` гарантує:
     * - що змінна не буде кешована ядром процесора;
     * - що її значення буде читатись/записуватись напряму з/в оперативну пам’ять;
     * - що всі потоки побачать актуальне значення без додаткової синхронізації.
     *
     * У цьому прикладі:
     * - Потік `main` оновлює значення `terminated`
     * - Потік `worker` перевіряє `terminated` у циклі
     */
    private volatile boolean terminated;

    @Override
    public void run() {
        // Цикл виконується доти, доки флаг terminated не стане true
        while (!terminated) {
            System.out.println("Running...");

            // Симулюємо роботу (щоб не засипати консоль виводом)
            try {
                Thread.sleep(500); // Затримка для імітації завантаження
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Worker terminated.");
    }

    /**
     * Цей метод дозволяє іншому потоку (наприклад main) зупинити поточний.
     * Саме тут ми змінюємо значення флагу, яке потік `run()` читає.
     */
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        Thread t1 = new Thread(worker);

        t1.start(); // Запускаємо новий потік, який крутиться в циклі

        // Чекаємо трохи, щоб побачити кілька ітерацій виводу
        Thread.sleep(500);

        // Через 500 мс сигналізуємо потоку зупинитись
        worker.setTerminated(true);
    }
}

/*
 * ВАЖЛИВО:
 * - volatile гарантує видимість змін змінної між потоками.
 * - Без volatile інші потоки можуть "не бачити" змінене значення
 *   через кешування в регістрах процесора або CPU кеші.
 * - volatile НЕ гарантує атомарність операцій.
 * - Для складніших операцій (наприклад, інкремент) потрібні додаткові механізми синхронізації.
 */
