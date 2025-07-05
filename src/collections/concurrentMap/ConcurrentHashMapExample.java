package collections.concurrentMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
- ConcurrentHashMap — потокобезпечна реалізація Map, яка дозволяє одночасний
  доступ з кількох потоків без зовнішньої синхронізації.
- На відміну від звичайного HashMap, ConcurrentHashMap не кидає ConcurrentModificationException
  при паралельних змінах.
- ConcurrentMap — інтерфейс, який реалізує ConcurrentHashMap.
- Переваги: висока продуктивність і безпека при доступі з багатьох потоків.
- Порядок виводу не гарантований, бо операції з map можуть відбуватися паралельно.
*/

public class ConcurrentHashMapExample {
  public static void main(String[] args) {
    ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();

    // Потік, який записує в мапу
    new Thread(() -> {
      // Записуємо кілька пар ключ-значення в карту
      map.put("A", 1);
      map.put("B", 10);
      map.put("C", 100);
    }).start();

    // Потік, який читає з мапи
    new Thread(() -> {
      try {
        Thread.sleep(1000); // Затримка, щоб дати можливість FirstWorker додати елементи
        // Читаємо і виводимо значення по ключах
        System.out.println(map.get("B"));
        System.out.println(map.get("A"));
        System.out.println(map.get("C"));
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }).start();
  }
}
