package collections.list.synchronyzedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
ВАЖЛИВО:
- Collections.synchronizedList() повертає потокобезпечна обгортка для List.
- Усі методи колекції синхронізовані на внутрішньому моніторі.
- Важливо: при ітерації по такому списку треба додатково синхронізуватись,
  інакше можливі ConcurrentModificationException.
- У цьому прикладі два потоки паралельно додають елементи у список.
- Завдяки synchronizedList, модифікації відбуваються без втрати даних.

Плюси:
Проста обгортка: Легко створити потокобезпечний список, обгорнувши будь-який існуючий List.
Синхронізує всі операції: Всі методи синхронізовані, тому не потрібно додатково думати про безпеку потоків.
Підходить для частих змін: Добре працює, якщо список часто змінюється (додаються/видаляються елементи).

Мінуси:
Висока конкуренція: Кожен виклик синхронізований на одному моніторі, що може викликати bottleneck при великій кількості потоків.
Ітерація треба синхронізувати вручну: Для безпечної ітерації потрібно обгорнути блок synchronized — інакше можливі ConcurrentModificationException.
*/

public class SynchronizedListExamle {
    public static void main(String[] args) {
        // Отримуємо потокобезпечний список
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++) {
                list.add(i);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++) {
                list.add(i);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Size: " + list.size()); // Очікуємо 2000
    }
}
