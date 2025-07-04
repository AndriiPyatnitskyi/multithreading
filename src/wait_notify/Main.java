package wait_notify;

class Process {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running produce method...");
            wait();
            System.out.println("Again in the produce method...");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);

        synchronized (this) {
            System.out.println("Running consume method...");
            notify();
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