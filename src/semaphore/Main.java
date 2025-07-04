package semaphore;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

enum Downloader {
    INSTANCE;

    // Semaphore видає перміти на одночасне виконнаня різними потоками
    private final Semaphore semaphore = new Semaphore(3, true);

    public void download() {
        try {
            semaphore.acquire();
            downloadData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    private void downloadData() {
        try {
            System.out.println("Downloading...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
public class Main {
    public static void main(String[] args) {
//        Executor executor = Executors.newCachedThreadPool();
//
//        for (int i = 0; i < 12; i++) {
//            executor.execute(Downloader.INSTANCE::download);
//        }
        for (int i = 0; i < 12; i++) {
            new Thread(Downloader.INSTANCE::download).start();
        }

    }
}
