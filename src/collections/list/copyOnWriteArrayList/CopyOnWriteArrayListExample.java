package collections.list.copyOnWriteArrayList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
ВАЖЛИВО:
- CopyOnWriteArrayList — потокобезпечна реалізація List.
- При кожній операції модифікації (add, remove і т.д.) створюється нова копія внутрішнього масиву.
- Ітератори, створені до модифікації, працюють з "старою" незмінною версією списку.
- Це забезпечує безпечне ітерування без ConcurrentModificationException,
  але робить модифікації дорогими по ресурсах.
- Підходить для списків з частими операціями читання та рідкими модифікаціями.

Плюси:
Безпечна ітерація без блокувань: Ітератори працюють над «знімком» списку, тому можна ітерувати без додаткової синхронізації.
Ідеально підходить для сценаріїв "багато читачів — мало писарів": Операції читання не блокуються і дуже швидкі.
Уникає ConcurrentModificationException: Оновлення створюють новий масив, тому ітерація не впливає на поточні читачі.

Мінуси:
Висока вартість запису: Кожна операція додавання або видалення створює копію внутрішнього масиву — це дорого з точки зору пам’яті і CPU.
Не підходить для частих змін: Якщо у вас багато змін у списку, це може суттєво погіршити продуктивність.
*/

public class CopyOnWriteArrayListExample {
  public static void main(String[] args) {
    CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});

    // Створюємо ітератор на поточний стан списку (1, 3, 5, 8)
    Iterator<Integer> iterator = numbers.iterator();

    // Додаємо елемент у список - створюється нова копія масиву
    numbers.add(10);

    // Ітератор не "бачить" змін і пройде по старій версії списку
    List<Integer> result = new LinkedList<>();
    iterator.forEachRemaining(result::add);

    // Виведе: [1, 3, 5, 8]
    System.out.println(result);

    // Створюємо новий ітератор — він бачить оновлений список з новим елементом
    Iterator<Integer> iterator2 = numbers.iterator();
    List<Integer> result2 = new LinkedList<>();
    iterator2.forEachRemaining(result2::add);

    // Виведе: [1, 3, 5, 8, 10]
    System.out.println(result2);
  }
}
