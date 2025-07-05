package collections.copyOnWriteArraySet;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/*
🔍 Що таке CopyOnWriteArraySet?

CopyOnWriteArraySet — це потокобезпечна реалізація множини (Set), яка базується на CopyOnWriteArrayList.
Кожного разу, коли множина модифікується (add, remove), створюється нова копія внутрішнього масиву.
Читачі (readers) працюють з незмінною копією, тому ітерація безпечна і не потребує блокувань.

---

✅ Переваги:
- Потокобезпечний без синхронізації (немає потреби в synchronized).
- Безпечна ітерація — не буде ConcurrentModificationException.
- Ідеально для сценаріїв з великою кількістю читань і малою кількістю змін.
- Всі стандартні Set-операції: унікальні значення, contains, remove.

---

⚠ Недоліки:
- Висока вартість модифікацій (add/remove), бо кожна операція створює новий масив.
- Не підходить для сценаріїв з частими змінами або великими наборами даних.
- Не гарантує сортування (як TreeSet або ConcurrentSkipListSet).

---

📦 Коли використовувати:
- Обробники подій (listeners), які додаються рідко, але читаються часто.
- Кеші або конфігурації, які змінюються нечасто.
- Дані, які переважно читаються у багатьох потоках без зміни.

---

🆚 Порівняння з ConcurrentSkipListSet:

| Особливість                     | CopyOnWriteArraySet        | ConcurrentSkipListSet       |
|-------------------------------|----------------------------|-----------------------------|
| Потокобезпека                  | ✅                         | ✅                          |
| Продуктивність на читання      | 🔼 Висока                  | 🔼 Висока                   |
| Продуктивність на зміну        | 🔽 Низька (копіює масив)   | ⚖ Помірна (fine-grained lock) |
| Витрати памʼяті                | 🔽 Більші                  | ⚖ Менші                    |
| Сортування                     | ❌                         | ✅ (natural ordering)       |
| Null-значення                  | ✅ Дозволені               | ❌ Заборонені               |
| Використання                  | Рідкі зміни                | Часті зміни                 |

---
*/

public class CopyOnWriteArraySetExample {
    public static void main(String[] args) {
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();

        set.add("Java");
        set.add("Concurrency");
        set.add("Java"); // Дублікат не буде доданий

        Iterator<String> iterator = set.iterator();

        // Додаємо новий елемент після створення ітератора
        set.add("Threads");

        System.out.println("Ітерація (старий знімок):");
        iterator.forEachRemaining(System.out::println); // Не побачить "Threads"

        System.out.println("Ітерація після оновлення:");
        for (String value : set) {
            System.out.println(value); // Побачить усі значення, включаючи "Threads"
        }
    }
}
