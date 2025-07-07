package threadLocal;

public class ThreadLocalCheatSheet {

    /**
     * ✅ Що таке ThreadLocal?
     *
     * ThreadLocal<T> — це змінна, значення якої зберігається окремо для кожного потоку.
     * Тобто кожен потік має свою власну копію значення.
     *
     * Основне використання:
     * - зберігання контексту потоку (наприклад, userId, transactionId)
     * - уникнення синхронізації, бо дані не спільні
     * - передача даних у межах одного потоку
     */

    // Простий ThreadLocal без значення за замовчуванням
    private static ThreadLocal<Integer> threadLocalId = new ThreadLocal<>();

    // ThreadLocal з початковим значенням
    private static ThreadLocal<String> defaultName = ThreadLocal.withInitial(() -> "Guest");

    public static void main(String[] args) {
        /**
         * 🔧 threadLocal.set(value) — встановлює значення для поточного потоку
         * 🔧 threadLocal.get() — отримує значення для поточного потоку
         * 🔧 threadLocal.remove() — очищує значення (важливо для уникнення memory leak)
         */

        Runnable task = () -> {
            int threadId = (int) (Math.random() * 1000);

            threadLocalId.set(threadId); // кожен потік отримує власне значення
            System.out.println(Thread.currentThread().getName() + " → ID: " + threadLocalId.get());

            // Приклад із ThreadLocal.withInitial
            System.out.println(Thread.currentThread().getName() + " → defaultName: " + defaultName.get());

            threadLocalId.remove();  // очищення після використання
            defaultName.remove();
        };

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");

        t1.start();
        t2.start();

        /**
         * ⚠️ Потенційні проблеми:
         *
         * ❌ Memory Leak — якщо не викликати .remove() у довгоживучих потоках (наприклад, у thread pool)
         * ❌ Значення не передається між потоками
         * ❌ Не підходить для асинхронних callback-ів (наприклад, CompletableFuture)
         */

        /**
         * 📌 ThreadLocal vs static:
         *
         * static                      | ThreadLocal
         * ----------------------------|-------------------------------
         * Загальне для всіх потоків   | Унікальне для кожного потоку
         * Потребує синхронізації      | Не потребує
         * Дані можуть бути змінені    | Дані тільки у власному потоці
         */
    }
}
