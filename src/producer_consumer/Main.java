package producer_consumer;

import java.util.ArrayList;
import java.util.List;

/*
ВАЖЛИВО:
- Використання synchronized(lock) гарантує, що лише один потік одночасно
  працює з буфером і коректно використовує wait/notify.

- Метод wait() вивільняє монітор і переводить потік у стан очікування,
  поки не буде викликаний notify() або notifyAll() на тому ж обʼєкті.

- notify() пробуджує випадковий потік, що чекає на lock, але монітор
  передається пробудженому потоку тільки після виходу з synchronized блоку.

- Для більш надійної реалізації використовують цикл while для перевірки
  умови, оскільки notify може пробудити потік "помилково" (spurious wakeup).

- Для більш масштабних рішень радять використовувати BlockingQueue або
  ReentrantLock з Condition, які дають більший контроль і простіші у використанні.
*/
class Processor {
    // Загальний буфер, у який виробник додає, а споживач забирає елементи
    private final List<Integer> list = new ArrayList<>();

    // Межі розміру буфера
    private static final Integer UPPER_LIMIT = 5;
    private static final Integer LOWER_LIMIT = 0;

    // Обʼєкт монітора для синхронізації wait/notify
    private final Object lock = new Object();

    // Значення, яке виробник додає в список
    private int value = 0;

    /**
     * Метод виробника (producer):
     * - Додає елементи у список, поки не досягне UPPER_LIMIT
     * - Якщо список повний, чекає (wait) поки споживач не звільнить місце
     * - Після додавання елемента викликає notify(), щоб розбудити споживача
     */
    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Adding to list is done...");
                    value = 0;
                    // Чекаємо, доки споживач звільнить місце (видалить елементи)
                    lock.wait();
                } else {
                    System.out.println("Adding to list: " + value);
                    list.add(value);
                    value++;
                    // Повідомляємо споживача, що є новий елемент
                    lock.notify();
                }
                // Імітація затримки (роботи)
                Thread.sleep(500);
            }
        }
    }

    /**
     * Метод споживача (consumer):
     * - Видаляє елементи зі списку, поки він не спорожніє
     * - Якщо список порожній, чекає (wait) доки виробник не додасть елементи
     * - Після видалення елемента викликає notify(), щоб розбудити виробника
     */
    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Removing from list is done...");
                    // Чекаємо, доки виробник не додасть елементи
                    lock.wait();
                } else {
                    // Видаляємо останній доданий елемент
                    System.out.println("Removing from list: " + list.remove(list.size() - 1));
                    // Повідомляємо виробника, що є вільне місце
                    lock.notify();
                }
                // Імітація затримки (роботи)
                Thread.sleep(500);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Processor processor = new Processor();

        Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Запускаємо потоки виробника і споживача
        t1.start();
        t2.start();
    }
}
