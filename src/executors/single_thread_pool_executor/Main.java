package executors.single_thread_pool_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable{
    private int id;

    public Task(int id) {
        this.id = id;
    }

    public void doWork() {
        System.out.println("Task with id: " + id + " is working... Thread name: " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        doWork();
    }
}

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task(i));
        }

        executorService.shutdown();
    }
}
