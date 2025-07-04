package collections.delayQueue;

import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class DelayedWorker implements Delayed {
  private long duration;

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public DelayedWorker(long duration, String message) {
    this.duration = System.currentTimeMillis() + duration;
    this.message = message;
  }

  private String message;
  @Override
  public long getDelay(TimeUnit unit) {
    return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
  }

  @Override
  public int compareTo(Delayed o) {
    return Long.compare(duration, ((DelayedWorker) o).duration);
  }

  @Override
  public String toString() {
    return "DelayedWorker{" +
        ", message='" + message + '\'' +
        '}';
  }
}

public class Main {

  public static void main(String[] args) {
    BlockingQueue<DelayedWorker> queue = new DelayQueue<>();

    try {
      queue.put(new DelayedWorker(2000, "First"));
      queue.put(new DelayedWorker(1000, "Second"));
      queue.put(new DelayedWorker(3000, "Third"));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    while (!queue.isEmpty()) {
      try {
        System.out.println(queue.take());
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
