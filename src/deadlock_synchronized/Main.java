package deadlock_synchronized;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
ВАЖЛИВО:
- Цей приклад демонструє класичну проблему "deadlock" (взаємне блокування).
- Два потоки намагаються захопити два локи (lock1 і lock2) в різному порядку.
- worker1 захоплює lock1, потім чекає і намагається захопити lock2.
- worker2 захоплює lock2, потім чекає і намагається захопити lock1.
- Якщо обидва потоки вчасно захоплять свій перший лок, вони будуть чекати
  один на одного вічно, бо другий лок зайнятий іншим потоком.
- synchronized(lock) блокує монітор об’єкта lock, тому тут використання Lock
  як обʼєкт монітора, а не як клас Lock API, може вводити в оману.
- Для уникнення дедлоку потрібно дотримуватись єдиного порядку блокування ресурсів.
*/

class Deadlock {
    final Lock lock1 = new ReentrantLock();
    final Lock lock2 = new ReentrantLock();

    public void worker1() {
        synchronized (lock1) { // Захоплюємо монітор lock1
            System.out.println("worker1 synchronized lock1");
            try {
                Thread.sleep(500); // Імітація роботи, щоб збільшити шанси на deadlock
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (lock2) { // Спроба захопити lock2
                System.out.println("worker1 synchronized lock2");
            }
        }
    }

    public void worker2() {
        synchronized (lock2) { // Захоплюємо монітор lock2
            System.out.println("worker2 synchronized lock2");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (lock1) { // Спроба захопити lock1
                System.out.println("worker2 synchronized lock1");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Deadlock deadlock = new Deadlock();

        // Два потоки запускаються паралельно,
        // кожен захоплює локи в різному порядку => потенційний deadlock
        new Thread(deadlock::worker1).start();
        new Thread(deadlock::worker2).start();
    }
}

/*
Пояснення:
- Deadlock відбувається через циклічну залежність потоків на ресурсах.
- Синхронізація через synchronized блокує монітор обʼєкта (lock1, lock2).
- Використання ReentrantLock як обʼєкт для synchronized — це не найкраща практика,
  бо краще використовувати самі методи lock() та unlock().
- Для уникнення deadlock:
    - Використовувати однаковий порядок захоплення локів у всіх потоках.
    - Використовувати методи tryLock() з таймаутом.
    - Використовувати більш високорівневі конструкції (java.util.concurrent).
*/
