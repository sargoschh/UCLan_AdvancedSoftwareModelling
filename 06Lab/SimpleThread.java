public class SimpleThread extends Thread {
    private String name;

    public SimpleThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " – Count: " + i);
            try {
                Thread.sleep(400); // simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SimpleThread t1 = new SimpleThread("Thread-A");
        SimpleThread t2 = new SimpleThread("Thread-B");
        t1.start();
        t2.start();
    }
}