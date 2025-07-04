package use_volatile;

class Worker implements Runnable {

    // Дуже важливо щоб volatile використовувася якщо тред зупиняється через флаг terminated із другого потока.
    // volatile не кешує змінну в ядрі процесора. а виносить це в спільну для ядер пам'ять.
    private volatile boolean terminated;

    @Override
    public void run() {
        while (!terminated) {
            System.out.println("Running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

}
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        Thread t1 = new Thread(worker);
        t1.start();

        Thread.sleep(500);
        worker.setTerminated(true);

    }
}
