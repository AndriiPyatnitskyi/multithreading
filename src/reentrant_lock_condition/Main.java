package reentrant_lock_condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Process {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();
        System.out.println("Running produce method...");
        condition.await();
        System.out.println("Again in the produce method...");
        lock.unlock();
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("Running consume method...");
        condition.signal();
        lock.unlock();
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