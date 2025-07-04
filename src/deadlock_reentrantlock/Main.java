package deadlock_reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
ВАЖЛИВО:
- Цей приклад також демонструє потенційний deadlock, але вже з використанням ReentrantLock.
- Потік worker1 захоплює lock1, потім через затримку намагається захопити lock2.
- Потік worker2 навпаки спочатку захоплює lock2, потім lock1.
- Якщо обидва потоки захоплять свій перший лок і почнуть чекати другий — виникне deadlock.
- Важливо завжди дотримуватися єдиного порядку захоплення локів, щоб уникнути deadlock.
- lock() блокує потік доки лок не буде доступним.
- unlock() має бути викликаний для звільнення локів, бажано у finally-блоці (тут для простоти не показано).
*/

class Deadlock {
    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();

    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 lock1.lock()");
        try {
            Thread.sleep(500); // Імітація роботи
            lock2.lock();
            System.out.println("Worker1 lock2.lock()");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Правильне звільнення локів у зворотному порядку
            lock2.unlock();
            System.out.println("Worker1 lock2.unlock()");
            lock1.unlock();
            System.out.println("Worker1 lock1.unlock()");
        }
    }

    public void worker2() {
        lock2.lock();
        System.out.println("Worker2 lock2.lock()");
        try {
            Thread.sleep(500);
            lock1.lock();
            System.out.println("Worker2 lock1.lock()");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock1.unlock();
            System.out.println("Worker2 lock1.unlock()");
            lock2.unlock();
            System.out.println("Worker2 lock2.unlock()");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Deadlock deadlock = new Deadlock();
        new Thread(deadlock::worker1).start();
        new Thread(deadlock::worker2).start();
    }
}

/*
Як уникнути deadlock у цьому прикладі:
- Виконувати захоплення локів у однаковому порядку у всіх потоках, наприклад:
  worker1 і worker2 спочатку lock1.lock(), потім lock2.lock().
- Або використовувати tryLock() з таймаутом, щоб не застрягати в очікуванні.
- Завжди викликати unlock() у finally-блоці, щоб гарантувати звільнення локів.
*/
