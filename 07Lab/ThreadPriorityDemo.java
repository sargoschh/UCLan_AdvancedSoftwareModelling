public class ThreadPriorityDemo {
    public static void main(String[] args) {
        // Create and start 5 threads with random priorities
        for (int i = 1; i <= 5; i++) {
            Thread t = new Thread(new Task(), "Thread-" + i);
            // Set a random priority between 1 and 10
            int priority = 1 + (int) (Math.random() * 10);
            t.setPriority(priority);
            System.out.println(t.getName() + " has priority: " + priority);
            t.start();
        }
    }

    // Simple task that prints the thread name and a loop counter
    static class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " running: " + i);
                try {
                    Thread.sleep(100); // Sleep briefly to simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}