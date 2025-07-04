package collections.delayQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/*
ВАЖЛИВО:
- DelayQueue — це потокобезпечна черга, яка дозволяє витягувати елементи лише після того,
  як їх "затримка" (delay) сплине.
- Елементи мають реалізовувати інтерфейс Delayed.
- Метод getDelay() повертає залишок часу до моменту, коли елемент стане доступним.
- Поки delay не закінчився, елемент не може бути отриманий методом take().
- Черга сама відсортована за часом затримки — елемент з найкоротшою затримкою буде отриманий першим.
*/

class DelayedWorker implements Delayed {
    private long duration;  // час, коли елемент стає доступним (в мс від епохи)
    private String message;

    public DelayedWorker(long delayMillis, String message) {
        // Зберігаємо момент часу, коли цей елемент стане доступним
        this.duration = System.currentTimeMillis() + delayMillis;
        this.message = message;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        // Повертаємо скільки часу ще залишилось (у вказаних одиницях)
        return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        // Порівнюємо об'єкти за часом доступності
        return Long.compare(this.duration, ((DelayedWorker) o).duration);
    }

    @Override
    public String toString() {
        return "DelayedWorker{" +
            "message='" + message + '\'' +
            '}';
    }
}

public class Main {

    public static void main(String[] args) {
        BlockingQueue<DelayedWorker> queue = new DelayQueue<>();

        try {
            // Додаємо елементи з різними затримками
            queue.put(new DelayedWorker(2000, "First"));   // 2 секунди
            queue.put(new DelayedWorker(1000, "Second"));  // 1 секунда
            queue.put(new DelayedWorker(3000, "Third"));   // 3 секунди
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Витягуємо елементи, вони будуть повертатись у порядку затримки
        while (!queue.isEmpty()) {
            try {
                System.out.println(queue.take());  // блокуватись, поки не буде доступного елемента
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
