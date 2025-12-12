import java.util.concurrent.locks.ReentrantLock;

public class RaceConditionDemo2 {
    private static int counter = 0;
    private static ReentrantLock lock = new ReentrantLock();

    public static void increment() {
        lock.lock(); // acquire lock
        try {
            counter++;
        } finally {
            lock.unlock(); // always release
        }
    }

    // public static void increment() {
    //     counter++;
    // }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++)
                increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++)
                increment();
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Final counter = " + counter);
    }
}