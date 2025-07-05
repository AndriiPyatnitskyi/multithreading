package synchronizers;

import java.util.concurrent.Semaphore;

/*
Пояснення та поради:
- Semaphore — механізм контролю кількості потоків, які можуть одночасно
  отримати дозвіл (permit) на виконання певної частини коду.
- У цьому прикладі Semaphore з 3 пермітами означає, що одночасно максимум
  3 потоки можуть працювати у методі download().
- Метод acquire() блокує потік, якщо всі перміти зайняті, доки один не звільниться.
- Метод release() звільняє перміт, даючи змогу іншому потоку увійти.
- Другий параметр конструктора (true) встановлює справедливий порядок
  надання пермітів (FIFO).
- Завжди виконуй release() у finally-блоці, щоб уникнути блокування.
- Для неблокуючої перевірки можна використати tryAcquire() із таймаутом.
*/

enum Downloader {
    INSTANCE;

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
            System.out.println("Downloading... Thread: " + Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class SemaphoreExample {
    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            new Thread(Downloader.INSTANCE::download, "Downloader-" + i).start();
        }
    }
}
