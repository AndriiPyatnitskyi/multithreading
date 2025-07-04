package collections.concurentMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class FirstWorker implements Runnable {
  ConcurrentMap<String, Integer> map;

  public FirstWorker(ConcurrentMap<String, Integer> map) {
    this.map = map;
  }

  @Override
  public void run() {
    map.put("A", 1);
    map.put("B", 10);
    map.put("C", 100);
  }
}


class SecondWorker implements Runnable {
  ConcurrentMap<String, Integer> map;

  public SecondWorker(ConcurrentMap<String, Integer> map) {
    this.map = map;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(1000);
      System.out.println(map.get("B"));
      System.out.println(map.get("A"));
      System.out.println(map.get("C"));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }
}

public class Main {
  public static void main(String[] args) {
    ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
    new Thread(new FirstWorker(map)).start();
    new Thread(new SecondWorker(map)).start();
  }
}
