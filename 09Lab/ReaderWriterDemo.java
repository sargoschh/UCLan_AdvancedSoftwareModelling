import java.util.concurrent.locks.*;

public class ReaderWriterDemo {
    private static int data = 0;
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        Runnable reader = () -> {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " read:" + data);
            } finally {
                lock.readLock().unlock();
            }
        };
        Runnable writer = () -> {
            lock.writeLock().lock();
            try {
                data++;
                System.out.println(Thread.currentThread().getName() + " wrote: " + data);
            } finally {
                lock.writeLock().unlock();
            }
        };
        // Start readers and writers
        new Thread(reader, "Reader-1").start();
        new Thread(reader, "Reader-2").start();
        new Thread(writer, "Writer-1").start();
        new Thread(reader, "Reader-3").start();
    }
}