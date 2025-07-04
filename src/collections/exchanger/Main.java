package collections.exchanger;

import static java.util.concurrent.CompletableFuture.runAsync;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;

public class Main {
  public static void main(String[] args) {
    Exchanger<String> exchanger = new Exchanger<>();

    Runnable taskA = () -> {
      try {
        String message = exchanger.exchange("from A");
//        assertEquals("from B", message);
        System.out.println("taskA " + message);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    Runnable taskB = () -> {
      try {
        String message = exchanger.exchange("from B");
//        assertEquals("from A", message);
        System.out.println("taskB " +   message);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };
    CompletableFuture.allOf(
        runAsync(taskA), runAsync(taskB)).join();
  }
}
