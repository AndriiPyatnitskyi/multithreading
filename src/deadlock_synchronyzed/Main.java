package deadlock_synchronyzed;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Deadlock {
    final Lock lock1 = new ReentrantLock();
    final Lock lock2 = new ReentrantLock();
    public void worker1() {

        synchronized (lock1) {
            System.out.println("worker1 synchronized lock1");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (lock2) {
                System.out.println("worker1 synchronized lock2");
            }
        }

    }

    public void worker2() {
        synchronized (lock2) {
            System.out.println("worker2 synchronized lock2");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (lock1) {
                System.out.println("worker2 synchronized lock1");
            }
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
