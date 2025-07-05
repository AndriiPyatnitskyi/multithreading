package basics;

/**
 * Демонстрація створення потоків у Java.
 * Два способи:
 * - Через анонімний клас/лямбду
 * - Через окрему реалізацію інтерфейсу Runnable
 */
class RunnableImpl implements Runnable {

    @Override
    public void run() {
        System.out.println("From RunnableImpl...");
    }
}

public class Main {
    public static void main(String[] args) {

        // ✅ 1. Запуск потоку через лямбду (анонімна реалізація Runnable)
        Thread thread1 = new Thread(() -> System.out.println("From Runnable..."));
        thread1.start();

        // ✅ 2. Запуск потоку через окремий клас, який реалізує Runnable
        Thread thread2 = new Thread(new RunnableImpl());
        thread2.start();

        // Обидва підходи створюють нові потоки, що виконують логіку з run()
    }
}
