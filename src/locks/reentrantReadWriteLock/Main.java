package locks.reentrantReadWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * ReentrantReadWriteLock дозволяє розділяти доступ до ресурсу між потоками,
 * які лише читають (read lock), і потоками, які хочуть писати (write lock).
 *
 * Особливості:
 * - Одночасно може бути багато читачів (read lock), якщо немає писарів.
 * - Писар отримує ексклюзивний доступ (write lock), блокуючи інших читачів і писарів.
 * - Lock є реентерантним — потік може захопити lock повторно.
 *
 * Цей клас підходить для задач, де читання зустрічається частіше, ніж запис.
 */
 class DataContainer {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int data = 0;

    /**
     * Метод читання даних.
     * Захоплює read lock, який дозволяє одночасний доступ з іншими читачами.
     */
    public int readData() {
        lock.readLock().lock();  // Захоплюємо read lock
        try {
            // Тут можна безпечно читати shared data, поки немає писачів
            System.out.println("Reading data: " + data + " by " + Thread.currentThread().getName());
            return data;
        } finally {
            lock.readLock().unlock();  // Обов’язково звільняємо read lock
        }
    }

    /**
     * Метод запису даних.
     * Захоплює write lock, який є ексклюзивним.
     */
    public void writeData(int newValue) {
        lock.writeLock().lock();  // Захоплюємо write lock
        try {
            System.out.println("Writing data: " + newValue + " by " + Thread.currentThread().getName());
            data = newValue;
            // Після цього читачі не зможуть читати, доки ми не звільнимо lock
        } finally {
            lock.writeLock().unlock();  // Звільняємо write lock
        }
    }
}

class Reader implements Runnable {
    private final DataContainer container;

    public Reader(DataContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            container.readData();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Writer implements Runnable {
    private final DataContainer container;

    public Writer(DataContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            container.writeData(i * 10);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {
        DataContainer container = new DataContainer();

        Thread writerThread = new Thread(new Writer(container), "WriterThread");
        Thread readerThread1 = new Thread(new Reader(container), "ReaderThread1");
        Thread readerThread2 = new Thread(new Reader(container), "ReaderThread2");

        writerThread.start();
        readerThread1.start();
        readerThread2.start();
    }
}

