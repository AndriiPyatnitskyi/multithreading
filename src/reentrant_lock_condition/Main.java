package reentrant_lock_condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
ВАЖЛИВО:
- ReentrantLock — це альтернатива synchronized, яка дає більше контролю над блокуванням.
- Condition пов’язаний з Lock і дозволяє потокам чекати і сигналити (await/signal),
  схоже на wait/notify, але з більшою гнучкістю.
- Перед викликом await() треба обов’язково захопити lock.lock().
- await() — поток віддає блокування і переходить у режим очікування сигналу.
- signal() — пробуджує один потік, який чекає на цьому Condition.
- Після signal() пробуджений потік знову має зачекати, доки lock буде звільнений.
- Важливо викликати lock.unlock() у finally блоці або після завершення роботи,
  щоб не створити deadlock.
*/

class Process {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Running produce method...");
            // Потік чекає сигналу і віддає lock
            condition.await();
            System.out.println("Again in the produce method...");
        } finally {
            lock.unlock();  // Гарантуємо звільнення блоку навіть при виключеннях
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000); // Імітація затримки
        lock.lock();
        try {
            System.out.println("Running consume method...");
            // Посилаємо сигнал одному з потоків, що чекають на condition
            condition.signal();
        } finally {
            lock.unlock();  // Гарантуємо звільнення
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Process process = new Process();

        Thread t1 = new Thread(() -> {
            try {
                process.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.setPriority(10);

        Thread t2 = new Thread(() -> {
            try {
                process.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t2.setPriority(1);

        t1.start();
        t2.start();
    }
}
