public class ProducerAndConsumer {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        new Thread(new Producer(buffer)).start();
        new Thread(new Consumer(buffer)).start();
    }
}

class Buffer {
    private String[] items = new String[5];
    private int count = 0;

    public synchronized void produce(String item) {
        while (count == items.length) { // buffer full
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        items[count++] = item;
        notifyAll();
    }

    public synchronized String consume() {
        while (count == 0) { // buffer empty
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        String item = items[--count];
        notifyAll();
        return item;
    }
}

class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer b) {
        buffer = b;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            String item = "Item" + i;
            System.out.println("Producing: " + item);
            buffer.produce(item);
        }
    }
}

class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer b) {
        buffer = b;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            String item = buffer.consume();
            System.out.println("Consuming: " + item);
        }
    }
}