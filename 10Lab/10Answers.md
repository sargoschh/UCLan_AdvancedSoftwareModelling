
# CO3408 – Week 10 Lab: Thread Pool and Efficient Multithreading

This lab focuses on understanding the efficiency and benefits of using a **thread pool** compared to manually creating and managing threads.

## Learning Outcomes

By completing this lab, you will be able to:
* See why creating threads manually is **expensive**.
* Observe **thread reuse**.
* Understand the importance of the `shutdown()` method.
* Understand **scalability** in multithreaded applications.

---

## Lab Task 1: Fixed Thread Pool Execution

**Objective:** Write a Java program to execute tasks using a `FixedThreadPool` and observe thread reuse.

### Requirements:
1.  Create a fixed thread pool of size **5**.
2.  Submit **10 tasks** to the pool.
3.  Each task should print the **current thread name** and a message.
4.  Shutdown the pool gracefully.

### Example Implementation:

```java
import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {
        // 1. Creates a fixed thread pool of size 5
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 2. Submits 10 tasks
        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            // The submit method accepts a Runnable (the task)
            executor.submit(() -> {
                // 3. Each task prints: current thread name and a message
                System.out.println("Task " + taskId + " executed by " +
                    Thread.currentThread().getName());
            });
        }

        // 4. Shutdown the pool gracefully
        executor.shutdown();
    }
}
````

  * **Observation:** In the output, you will notice that the 10 tasks are handled by only 5 threads (e.g., `pool-1-thread-1` through `pool-1-thread-5`). [cite_start]This demonstrates **thread reuse**, which is the core benefit of a thread pool[cite: 4].

-----

## Lab Task 2 - Compare Threads vs Thread Pool

**Objective:** Modify the code to use manual thread creation and measure the execution time to compare its performance against the thread pool.

### Requirements:

1.  Instead of using `ExecutorService`, use `new Thread()` manually.
2.  Run the same number of tasks (or a larger number, like 1000 in the example, for a clearer difference).
3.  Measure the execution time.

### Example Implementation:

```java
public class ManualThreads {
    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        System.out.println("Thread start = " + start);

        // Submitting 1000 tasks, each running in a new, manually created thread
        for (int i = 1; i <= 1000; i++) {
            Thread t = new Thread(() -> {
                try {
                    // Simulate some work
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            // t.join() forces the main thread to wait for the current thread to finish
            // before starting the next one. Removing t.join() would show faster
            // execution but would still incur the overhead of 1000 thread creations.
            t.join();
        }

        long end = System.currentTimeMillis();
        System.out.println("Manual thread time = " + (end - start) + " ms");
    }
}
```

-----

## Lab Questions / Discussion

### 1. Which approach is faster?

**Answer:** The **Thread Pool (`ExecutorService`) approach is significantly faster** when dealing with a large number of short-lived tasks.

### 2. Why?

**Explanation:**

The performance difference comes down to the **overhead of thread creation and destruction**.

  * **Manual Thread Creation:** Every time you call `new Thread()` and `t.start()`, the operating system has to allocate resources for a brand-new thread. This involves:

      * Kernel calls.
      * Memory allocation for the stack.
      * The overhead of scheduling the new thread.
      * After the thread finishes, the resources must be deallocated.
      * When running thousands of tasks, this constant creation and disposal of threads is computationally **expensive** and time-consuming, leading to poor **scalability**.

  * **Thread Pool (`ExecutorService`):** A thread pool maintains a set of **pre-initialized, idle worker threads**.

      * When a task is submitted, the pool simply picks an available thread and assigns the task to it (this is known as **thread reuse**).
      * The expensive part (thread creation) is done only once when the pool is initialized.
      * Once the task is complete, the thread does not die; it returns to the pool and waits for the next task.
      * This eliminates the thread creation/destruction overhead for every task, leading to much better performance and **scalability** for high-volume task processing.

