package executors.scheduled_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Task implements Runnable{
    public void doWork() {
        System.out.println("Task with is working... Thread name: " + Thread.currentThread().getName());
    }

    @Override
    public void run() {
        doWork();
    }
}

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Task(), 1, 1, TimeUnit.SECONDS);
    }
}
