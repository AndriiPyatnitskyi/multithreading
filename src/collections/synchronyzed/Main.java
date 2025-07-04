package collections.synchronyzed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
ВАЖЛИВО:
- Collections.synchronizedList() повертає потокобезпечний обгорток для List.
- Усі методи колекції синхронізовані на внутрішньому моніторі.
- Важливо: при ітерації по такому списку треба додатково синхронізуватись,
  інакше можливі ConcurrentModificationException.
- У цьому прикладі два потоки паралельно додають елементи у список.
- Завдяки synchronizedList, модифікації відбуваються без втрати даних.
*/

public class Main {
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
