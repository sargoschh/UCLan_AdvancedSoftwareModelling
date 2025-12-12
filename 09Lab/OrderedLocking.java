public class OrderedLocking {
    private static final Object A = new Object();
    private static final Object B = new Object();

    public static void acquireLocks() {
        synchronized (A) {
            synchronized (B) {
                System.out.println(Thread.currentThread().getName() + " acquired A and B");
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(OrderedLocking::acquireLocks);
        Thread t2 = new Thread(OrderedLocking::acquireLocks);
        t1.start();
        t2.start();
    }
}