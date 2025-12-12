# Lab 08 - Advanced Synchronization - Semaphores and Scheduling

## Task 1: Preemptive vs Non-Preemptive Scheduling

Explain the difference between preemptive and non-preemptive scheduling in your own words

**Preemptive Scheduling**
- In a preemptive system, the CPU can forcibly switch from one thread (or process) to another.

- A running thread can be interrupted or "preempted" by the operating system's scheduler to allow another thread to run, typically based on priority or a time slice.

**Non-Preemptive Scheduling**

- In a non-preemptive system, a running thread keeps the CPU until it either finishes its task, voluntarily yields control, or blocks (e.g., waiting for I/O).

- The thread controls when it gives up the CPU.

Comment: Java threads typically behave preemptively. Since the program has a loop that runs in two separate threads without any explicit sleep() or I/O operations (which would cause a yield/block), if it were non-preemptive, one thread might completely finish its loop (printing 1 through 5) before the other thread even gets a chance to start. The observed interleaved output confirms that the scheduler is interrupting the running thread and switching the CPU to the other thread, which is the definition of preemption.

## Task 2: Mutual Exclusion with Race Condition

### Observing the Race Condition

Initial Program (Race Condition Version), the code for increment() initially looks like this:

```Java
public static void increment() {
    counter++;
}
```
When this increment() method is called 10,000 times by Thread-t1 and 10,000 times by Thread-t2, the expected final counter value should be 20,000.

When run without mutual exclusion, the final counter value will likely be less than 20,000. It will also vary every time you run the program.

This happens because the operation counter++ is not atomic. It involves multiple steps: read the current value, increment it, and write the new value back.

If one thread reads the value, and then the CPU switches to the other thread which also reads, increments, and writes, the first thread will increment its stale value and write it back, causing one increment operation to be lost. This is a race condition.

### Fixing the Race Condition (Mutual Exclusion)

The problem is fixed by ensuring mutual exclusion. This means that only one thread can access and modify the shared resource (counter) at any given time.

Corrected increment() Method (Using ReentrantLock):

```Java

    import java.util.concurrent.locks.ReentrantLock;
    // ...
    private static ReentrantLock lock = new ReentrantLock(); // Initialized lock
    // ...
    public static void increment() {
        lock.lock(); // acquire lock
        try {
            counter++; // critical section
        } finally {
            lock.unlock(); // always release
        }
    }
    // ...
```
    Correct Output With the lock in place, the threads will wait their turn to access counter++. The final output will consistently be:

    Final counter = 20000 

## Task 3: Use a Semaphore to Limit Access

The task uses a binary semaphore (a semaphore initialized with a count of 1) to achieve mutual exclusion, similar to a lock.

How the Semaphore Works:

1. The Semaphore is initialized with new Semaphore(1).

2. When a thread (say Thread-1) runs, it first prints "waiting..." and then calls semaphore.acquire().

    - Since the count is 1, Thread-1 immediately gets the permit, and the count drops to 0.

    - It prints "entered critical section" and simulates work (Thread.sleep(500)).

3. While Thread-1 is inside the critical section, Thread-2 will also call semaphore.acquire().

    - Since the count is 0, Thread-2 will be blocked (wait) until a permit is released.

4. Thread-1 finishes its work, prints "leaving..." , and then calls semaphore.release() in the finally block.

    - This returns the permit, and the count goes back to 1.

5. The scheduler immediately unblocks Thread-2, which then acquires the permit (count goes to 0), prints "entered critical section," and proceeds.

Expected Output Pattern The output demonstrates that only one thread enters the critical section at a time, enforcing mutual exclusion.

````
Thread-1 waiting...
Thread-2 waiting...
Thread-1 entered critical section
Thread-1 leaving...
Thread-2 entered critical section
Thread-2 leaving...
````