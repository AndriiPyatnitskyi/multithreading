package collections.concurrentQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
🔎 ОПИС:
ConcurrentLinkedQueue — це неблокуюча потокобезпечна черга, яка базується на алгоритмах без блокувань (lock-free).
Вона реалізує чергу FIFO (first-in-first-out), ідеальна для сценаріїв з великою кількістю потоків,
які часто додають та видаляють елементи.

⚙ ОСОБЛИВОСТІ:
- Потокобезпечна без використання synchronized або блокувань.
- Немає блокування під час додавання/видалення елементів.
- Методи `offer()` і `poll()` не блокують потоки.
- Висока продуктивність у середовищі з великою конкуренцією потоків.
- Підходить для простого обміну даними між потоками.

📌 КОЛИ ВИКОРИСТОВУВАТИ:
- Коли потрібна неблокуюча черга.
- Коли можливе часте додавання/видалення елементів з кількох потоків.
- Коли затримки від блокувань є критичними.

*/

public class ConcurrentLinkedQueueExample {

    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedQueue<>();

        // Потік 1 додає елементи
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                String item = "item-" + i;
                queue.offer(item); // додаємо елемент до черги
                System.out.println("Produced: " + item);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Потік 2 споживає елементи
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                while (true) {
                    String item = queue.poll(); // намагаємось забрати елемент
                    if (item != null) {
                        System.out.println("Consumed: " + item);
                        break;
                    }
                    try {
                        Thread.sleep(50); // чекаємо, поки з'явиться елемент
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
