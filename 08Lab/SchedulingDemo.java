public class SchedulingDemo {
 public static void main(String[] args) {
 Runnable r = () -> {
 for (int i = 1; i <= 5; i++) {
 System.out.println(Thread.currentThread().getName() + " → " + 
i);
 // No sleep here — one thread may dominate if non-preemptive
 }
 };
 Thread t1 = new Thread(r, "Thread-A");
 Thread t2 = new Thread(r, "Thread-B");
 t1.start();
 t2.start();
 }
}