import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    private static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            try {
                System.out.println(name + " waiting...");
                semaphore.acquire(); // request permit
                System.out.println(name + " entered critical section");
                Thread.sleep(500); // simulate work
                System.out.println(name + " leaving...");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                semaphore.release(); // release permit
            }
        };
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        t1.start();
        t2.start();
    }
}