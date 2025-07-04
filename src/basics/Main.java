package basics;

class RunnableImpl implements Runnable {

    @Override
    public void run() {
        System.out.println("From RunnableImpl...");
    }
}

public class Main {
    public static void main(String[] args) {
        new Thread(() -> System.out.println("From Runnable..."))
                .start();
    }
}

