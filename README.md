# Java Multithreading Course Repository

Цей репозиторій містить приклади та завдання, які я проходив під час курсу з багатопоточності на Java. Тут зібрані базові та просунуті концепції, ідеально структуровані для послідовного вивчення.

---

## Навчальний план

### 1. Basics

Основи роботи з потоками у Java: створення потоків, інтерфейс `Runnable`, ключове слово `volatile` для видимості змін у пам’яті, `final` для незмінних об’єктів.

**Класи:**

* `basics.Main`
* `memoryvisibility.Main`

---

### 2. Wait/Notify

Механізм базової синхронізації за допомогою `wait()` і `notify()` всередині `synchronized` блоків. Як працюють монітори та черга очікування потоків.

**Класи:**

* `wait_notify.Main`
* `producer_consumer.Main`

---

### 3. ReentrantLock + Condition

Більш гнучка альтернатива `synchronized/wait/notify` — використання `ReentrantLock` і умов (`Condition`) для синхронізації.

**Класи:**

* `reentrant_lock_condition.Main`

---

### 4. ReadWrite Locks

Локи для розмежування читання і запису, що дозволяють більш високу конкурентність:

- `ReentrantReadWriteLock` — стандартний read-write lock
- `StampedLock` — більш продуктивний lock з підтримкою оптимістичних та пессимістичних режимів блокування

**Класи:**

* `reentrantreadwritelock.Main`
* `stampedlock.Main`

---

### 5. BlockingQueue

Сучасний та безпечний спосіб організації producer-consumer через `BlockingQueue`. Не потрібно явно писати `wait/notify`, усе керується чергою.

**Класи:**

* `collections.blockingQueue.Main`
* `collections.priorityQueue.Main`
* `collections.delayQueue.Main`

---

### 6. Thread-safe Collections

Огляд потокобезпечних колекцій в Java:

**Класи:**

* `collections.concurentMap.Main`
* `collections.synchronyzed.Main`
* `collections.copyOnWriteArrayList.Main`

---

### 7. Semaphore

Обмеження одночасного доступу до ресурсів за допомогою семафорів. Приклад ліміту одночасних завантажень.

**Класи:**

* `semaphore.Main`

---

### 8. Executors Framework

Менеджмент потоків через пул потоків (`ExecutorService`), включаючи:

* `FixedThreadPool` — фіксований пул потоків
* `SingleThreadExecutor` — один потік
* `ScheduledExecutor` — планувальник завдань
* Використання `Callable` і `Future` для отримання результатів та обробки винятків

**Класи:**

* `executors.fixed_thread_pool_executor.Main`
* `executors.single_thread_pool_executor.Main`
* `executors.scheduled_executor.Main`
* `executors.callable.Main`

---

### 9. Deadlock

Вивчення причин та прикладів deadlock’у з використанням `synchronized` і `ReentrantLock`.

**Класи:**

* `deadlock_synchronyzed.Main`
* `deadlock_reentrantlock.Main`

---

### 10. Advanced Synchronizers and Queues

Потокова синхронізація з бар’єрами, лічильниками, обмінниками та пріоритетними чергами:

**Класи:**

* `collections.cyclicBarier.Main` — `CyclicBarrier`
* `collections.latch.Main` — `CountDownLatch`
* `collections.exchanger.Main` — `Exchanger`
* `collections.priorityQueue.Main` — `PriorityBlockingQueue`
* `collections.delayQueue.Main` — `DelayQueue`

---

## Як запускати

Кожен пакет має клас `Main` з точкою входу `public static void main(String[] args)`. Можна запускати кожен приклад окремо для ознайомлення.

---

## Подальші кроки

* Розширювати коментарі та докладно розбирати код
* Писати додаткові тести і завдання
* Впроваджувати сучасні підходи: `CompletableFuture`, `Project Loom` (якщо цікаво)
* Налагоджувати структуру під Maven/Gradle для зручності

---

## Теми для додавання

### 🔧 CompletableFuture

### 🔧 ThreadLocal

### 🔧 ForkJoinPool / RecursiveTask

### 🔧 Phaser

### 🔧 Virtual Threads (Project Loom)

### 🔧 Executors.newWorkStealingPool()
