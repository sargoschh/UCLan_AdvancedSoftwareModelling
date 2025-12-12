public class ManualThreads {
    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        System.out.println("Thread start = " + start);
        
        for (int i = 1; i <= 1000; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(10); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            t.join(); 
        }
        long end = System.currentTimeMillis();
        System.out.println("Manual thread time = " + (end - start) + " ms");
    }
}
