package wait_notify;

class Process {

    /**
     * Метод produce() блокує монітор this, виводить повідомлення і викликає wait(),
     * що переводить потік у стан очікування (releases lock).
     */
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running produce method...");

            /**
             * Потік віддає монітор this і переходить у чергу очікування.
             * Він буде "розбуджений", коли інший потік виконає notify().
             */
            wait();

            // Потік прокинувся після notify(), знову отримав монітор this
            System.out.println("Again in the produce method...");
        }
    }

    /**
     * Метод consume() — імітація логіки, яка "розбудить" потік produce().
     */
    public void consume() throws InterruptedException {
        // Затримка, щоб дати шанс t1 увійти в wait()
        Thread.sleep(1000);

        synchronized (this) {
            System.out.println("Running consume method...");

            /**
             * notify() пробуджує один потік, що чекає на цьому об'єкті.
             * Але не передає монітор автоматично — пробуджений потік мусить дочекатись,
             * поки this буде звільнено.
             */
            notify();
        }
    }

    /*
     * ВАЖЛИВО:
     * 1) wait() і notify() МОЖНА викликати ЛИШЕ всередині synchronized блоку або методу,
     *    інакше виникає IllegalMonitorStateException.
     *
     * 2) Якщо в черзі очікування кілька потоків — notify() пробуджує випадковий.
     *    Для пробудження всіх потоків використовується notifyAll().
     *
     * 3) notify() НЕ передає монітор автоматично;
     *    пробуджений потік зможе взяти монітор лише після того,
     *    як потік, що викликав notify(), звільнить synchronized блок.
     */

    /*
     * 🔍 ПОЯСНЕННЯ:
     * Метод           | Опис
     * -------------------------------------------------------
     * wait()          | Потік чекає (віддає монітор), доки його не "розбудять" notify()/notifyAll()
     * notify()        | Пробуджує один з потоків, який викликав wait() на цьому об’єкті
     * synchronized    | Блок чи метод, обов’язкові для використання wait/notify,
     *                 | інакше буде IllegalMonitorStateException
     */
}

public class Main {

    public static void main(String[] args) {

        Process process = new Process();

        // Потік, який виконує produce() — викликає wait()
        Thread t1 = new Thread(() -> {
            try {
                process.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Потік, який виконує consume() — викликає notify()
        Thread t2 = new Thread(() -> {
            try {
                process.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Пріоритети потоків. Не гарантують порядок виконання.
        t1.setPriority(10);
        t2.setPriority(1);

        // Запускаємо обидва потоки
        t1.start();
        t2.start();
    }
}
