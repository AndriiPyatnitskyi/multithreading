package deadlock_reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Deadlock {
    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();
    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 lock1.lock()");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println("Worker1 lock2.lock()");

        lock1.unlock();
        System.out.println("Worker1 lock1.unlock()");

        lock2.unlock();
        System.out.println("Worker1 lock2.unlock()");
    }

    public void worker2() {
        lock2.lock();
        System.out.println("Worker2 lock2.lock()");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock1.lock();
        System.out.println("Worker2 lock1.lock()");

        lock1.unlock();
        System.out.println("Worker2 lock1.unlock()");

        lock2.unlock();
        System.out.println("Worker2 lock2.unlock()");
    }
}

public class Main {
    public static void main(String[] args) {
        Deadlock deadlock = new Deadlock();
        new Thread(deadlock::worker1).start();
        new Thread(deadlock::worker2).start();
    }
}
