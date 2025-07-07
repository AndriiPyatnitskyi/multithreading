package completableFuture;

import java.util.concurrent.*;
import java.util.function.*;

public class CompletableFutureCheatSheet {

    /**
     * 🔹 CompletableFuture — клас для асинхронної обробки даних.
     *
     * Основні можливості:
     * - асинхронне виконання (runAsync, supplyAsync)
     * - ланцюжки thenApply / thenAccept / thenRun
     * - комбінування future-об'єктів
     * - обробка винятків
     * - тайм-аут
     * - ручне завершення
     * - "копіювання" future через whenComplete або completedFuture
     */

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // ✅ 1. Асинхронний запуск з поверненням значення
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Hello";
        });

        // ✅ 2. Ланцюгова обробка
        cf.thenApply(result -> result + " World")
                .thenAccept(System.out::println)
                .exceptionally(ex -> {
                    System.out.println("Error: " + ex.getMessage());
                    return null;
                });

        // ✅ 3. Комбінування двох CompletableFuture
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "First");
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "Second");

        f1.thenCombine(f2, (a, b) -> a + " + " + b)
                .thenAccept(System.out::println);

        // ✅ 4. Композиція: результат одного як вхід в інший
        CompletableFuture<String> composed = f1.thenCompose(res ->
                CompletableFuture.supplyAsync(() -> res + " composed"));
        composed.thenAccept(System.out::println);

        // ✅ 5. allOf: коли ВСІ futures завершені
        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);
        all.thenRun(() -> System.out.println("All done"));

        // ✅ 6. anyOf: коли ПЕРШИЙ завершено
        CompletableFuture<Object> any = CompletableFuture.anyOf(f1, f2);
        any.thenAccept(res -> System.out.println("First done: " + res));

        // ✅ 7. Ручне завершення
        CompletableFuture<String> manual = new CompletableFuture<>();
        manual.complete("Manual result");
        manual.thenAccept(System.out::println);

        // ✅ 8. Таймаути
        CompletableFuture<String> slowFuture = CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            return "Too slow";
        });

        slowFuture
                .orTimeout(1, TimeUnit.SECONDS)
                .exceptionally(ex -> "Recovered from timeout")
                .thenAccept(System.out::println);

        CompletableFuture<String> withDefault = CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            return "Too slow again";
        });

        withDefault
                .completeOnTimeout("Default value", 1, TimeUnit.SECONDS)
                .thenAccept(System.out::println);

        // ✅ 9. Копіювання CompletableFuture через whenComplete
        CompletableFuture<String> original = CompletableFuture.supplyAsync(() -> "Original Future");
        CompletableFuture<String> copy = copyFuture(original);
        copy.thenAccept(result -> System.out.println("Copied result: " + result));

        // ✅ 10. completedFuture — готова "копія" значення
        CompletableFuture<String> ready = CompletableFuture.completedFuture("Precomputed copy");
        ready.thenAccept(result -> System.out.println("Completed future value: " + result));

        // ✅ 11. Копіювання CompletableFuture через copy()
        // Attempting to complete the copied future will not affect the original
        // This line would have no effect if originalFuture was already completed.
        // If originalFuture was not yet completed, calling complete on copiedFuture
        // would only affect copiedFuture, not originalFuture.
        // copiedFuture.complete("New value on copy");
        CompletableFuture<String> originalFuture = new CompletableFuture<>();
        // Create a defensive copy
        CompletableFuture<String> copiedFuture = originalFuture.copy();
        // Complete the original future
        originalFuture.complete("Original value");
        // The copied future will also complete with the same value
        System.out.println("Copied future result: " + copiedFuture.get());
    }

    /**
     * Створює новий CompletableFuture, який завершиться тоді ж і тим же результатом або винятком, що й original.
     */
    public static <T> CompletableFuture<T> copyFuture(CompletableFuture<T> original) {
        CompletableFuture<T> copy = new CompletableFuture<>();
        original.whenComplete((res, ex) -> {
            if (ex != null) copy.completeExceptionally(ex);
            else copy.complete(res);
        });
        return copy;
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}

