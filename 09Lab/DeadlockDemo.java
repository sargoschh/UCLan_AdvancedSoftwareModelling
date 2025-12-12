public class DeadlockDemo {
    private static final Object A = new Object();
    private static final Object B = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                sleep(100);
                synchronized (B) {
                    System.out.println("t1 acquired A and B");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (B) {
                sleep(100);
                synchronized (A) {
                    System.out.println("t2 acquired B and A");
                }
            }
        });
        t1.start();
        t2.start();
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}