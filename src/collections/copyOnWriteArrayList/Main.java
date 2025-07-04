package collections.copyOnWriteArrayList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
  public static void main(String[] args) {
    CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});

    Iterator<Integer> iterator = numbers.iterator();

    numbers.add(10);

    List<Integer> result = new LinkedList<>();
    iterator.forEachRemaining(result::add);

    // contains only 1, 3, 5, 8
    System.out.println(result);

    Iterator<Integer> iterator2 = numbers.iterator();
    List<Integer> result2 = new LinkedList<>();
    iterator2.forEachRemaining(result2::add);

    //contains only 1, 3, 5, 8, 10
    System.out.println(result2);
  }
}
