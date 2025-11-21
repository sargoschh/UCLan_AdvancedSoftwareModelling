class Worker implements Runnable {
    private volatile boolean running = true;

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("Working...");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Worker stopped.");
    }
}

public class StopWorkerDemo {
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        Thread t = new Thread(worker);
        t.start();
        Thread.sleep(2000); // let the worker run
        worker.stop(); // stop worker
    }
}