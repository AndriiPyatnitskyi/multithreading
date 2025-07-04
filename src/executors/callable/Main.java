package executors.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Processor implements Callable<String> {
    private final int id;

    public Processor(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "Id: " + id + " Thread name: " + Thread.currentThread().getName();
    }
}

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
//
//        List<Future<String>> list = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            Future<String> future = service.submit(new Processor(i));
//            list.add(future);
//        }
        List<Future<String>> list = IntStream.range(0, 9)
                .mapToObj(Processor::new)
                .map(service::submit)
                .toList();

        list.stream()
                .map(stringFuture -> {
                    try {
                        return stringFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);

        service.shutdown();
    }

}
