package collections.concurrentMap;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/*
🔎 ОПИС:
ConcurrentSkipListMap — це потокобезпечна реалізація SortedMap / NavigableMap.
Вона підтримує впорядкованість ключів, як TreeMap, але на відміну від TreeMap:
- є потокобезпечною,
- базується не на дереві, а на skip list (структура з кількох рівнів списків),
- дозволяє ефективний доступ, пошук і навігацію в багатопоточному середовищі.

⚙ ОСОБЛИВОСТІ:
- Гарантовано впорядковує ключі за natural ordering або через Comparator.
- Без блокувань (lock-free reads, fine-grained locking for writes).
- Висока масштабованість у багатопоточному доступі.
- Ідеально підходить для конкурентних кешів, словників, черг з пріоритетом за ключами тощо.

📌 КОЛИ ВИКОРИСТОВУВАТИ:
- Коли потрібен потокобезпечний SortedMap.
- Коли важливий порядок ключів.
- Коли TreeMap не можна використовувати через відсутність синхронізації.

---
📕 1. Lock-free reads (читання без блокування)
Це означає, що читачі (threads, які читають) можуть:
переглядати вміст мапи,
виконувати операції типу get(), containsKey(), forEach()
без очікування або блокування інших потоків.
🔍 Наприклад:
String value = map.get(42); // Не блокує нікого
✅ Це можливо завдяки тому, що структура SkipList дозволяє неконфліктний паралельний доступ до елементів, і читачі не заважають один одному та навіть записувачам у багатьох випадках.

📕 2. Fine-grained locking for writes (локальні, дрібнозернисті блокування для запису)
Коли треба вставити, видалити чи оновити елемент (put, remove, replace), тоді вже використовується блокування — але:
✅ не глобальне (не блокується вся мапа),
✅ а локальне — лише частина списку, що стосується конкретного вузла або сегмента.
Це називається fine-grained locking — замість великого загального замка, система блокує лише те, що потрібно, і лише на короткий час.

| Операція       | TreeMap + synchronized | ConcurrentHashMap   | ConcurrentSkipListMap          |
| -------------- | ---------------------- | ------------------- | ------------------------------ |
| `get()`        | блокує всю мапу        | без блокування      | без блокування                 |
| `put()`        | блокує всю мапу        | блокує лише сегмент | блокує лише частину списку     |
| Порядок ключів | є                      | немає               | є                              |
| Паралелізм     | низький                | високий             | високий, з додатковим порядком |
*/

public class ConcurrentSkipListMapExample {
    public static void main(String[] args) {
        Map<Integer, String> map = new ConcurrentSkipListMap<>();

        // Потік для запису в мапу
        Thread writer = new Thread(() -> {
            for (int i = 5; i >= 1; i--) {
                map.put(i, "Value-" + i);
                System.out.println("Inserted: " + i + " -> Value-" + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Потік для читання з мапи
        Thread reader = new Thread(() -> {
            try {
                Thread.sleep(300); // трохи почекати, поки буде щось у мапі
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            map.forEach((key, value) -> System.out.println("Read: " + key + " -> " + value));
        });

        writer.start();
        reader.start();
    }
}
