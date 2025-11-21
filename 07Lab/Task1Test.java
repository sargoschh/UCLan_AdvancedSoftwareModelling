class HelloTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello from: " +
                Thread.currentThread().getName());
    }
}

public class Task1Test {
    public static void main(String[] args) {
        Thread t1 = new Thread(new HelloTask(), "T1");
        Thread t2 = new Thread(new HelloTask(), "T2");
        Thread t3 = new Thread(new HelloTask(), "T3");
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All threads completed!");
    }
}