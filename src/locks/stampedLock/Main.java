package locks.stampedLock;

import java.util.concurrent.locks.StampedLock;

/*
 * StampedLock — це більш новий вид локів у Java, що дозволяє
 * оптимістичне читання та більш гнучке керування блокуваннями.
 *
 * Особливості:
 * - Підтримує три режими блокування: write lock (ексклюзивний),
 *   read lock (спільний) та optimistic read (оптимістичне читання).
 * - Оптимістичне читання не блокує писачів, але потрібно перевіряти,
 *   чи не було змін під час читання.
 * - Якщо оптимістичне читання "спрацьовує" (дані не змінені),
 *   то це дуже швидко.
 * - Якщо дані змінені — fallback на повноцінне читання через readLock().
 */

class DataContainer {
    private final StampedLock lock = new StampedLock();
    private int data = 0;

    /**
     * Оптимістичне читання з fallback на повноцінний read lock.
     */
    public int readData() {
        long stamp = lock.tryOptimisticRead();  // Отримуємо "штамп" без блокування
        int currentData = data;                  // Читаємо значення (неблокуючись)
        // Перевіряємо, чи не було змін під час читання
        if (!lock.validate(stamp)) {
            // Дані могли змінитися, тому беремо read lock
            stamp = lock.readLock();
            try {
                currentData = data;  // Читаємо з блокуванням
            } finally {
                lock.unlockRead(stamp);  // Звільняємо read lock
            }
        }
        System.out.println("Read data: " + currentData + " by " + Thread.currentThread().getName());
        return currentData;
    }

    /**
     * Запис із використанням write lock (ексклюзивний доступ).
     */
    public void writeData(int newValue) {
        long stamp = lock.writeLock();  // Отримуємо write lock
        try {
            System.out.println("Writing data: " + newValue + " by " + Thread.currentThread().getName());
            data = newValue;
        } finally {
            lock.unlockWrite(stamp);  // Звільняємо write lock
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
