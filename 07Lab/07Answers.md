# Lab 07 - Modelling and Implementing Coordination in Concurrency

## Part 1 - Creating, Starting, and Waiting for Threads

### Task 1.1 Questions

1. What happens to the order of output? 

    The order of output is unpredictable and non-deterministic. The messages printed by the threads (T1, T2, T3) can appear in any sequence, such as T1, T3, T2, or T2, T1, T3, and the order may change on different runs.

2. Why is thread scheduling unpredictable?

    Thread scheduling is unpredictable because the Operating System's (OS) scheduler manages which thread runs, when it runs, and for how long. The scheduler's decision is influenced by many factors, including the number of CPU cores, the priority of other processes/threads, and the OS's specific scheduling algorithm, making the exact timing and order of execution non-deterministic.

### Task 1.2 Questions

1. How does join() change the behaviour of the program? 
    
    The join() method forces the calling thread (in this case, the main thread) to wait until the thread on which join() is called has finished its execution. In the provided code, t1.join(), t2.join(), and t3.join() ensure that the main thread will pause and only print "All threads completed!" after all three threads (T1, T2, and T3) have completed their respective run() methods.

2. Why might join() be important in real applications?

    join() is important when one thread's work depends on the completion or results of another thread.

    - Data Aggregation: It ensures all worker threads have finished processing their data before the main thread attempts to collect and aggregate the final results.

    - Resource Cleanup/Finalization: It can be used to ensure background threads have properly shut down or released resources before the application exits.

## Part 2 - Thread Priorities (Demonstration)

### Task 2.1 Questions

1. Do high-priority threads always run first?

    No, high-priority threads do not always run first. Setting a higher priority simply gives a thread a preference or a higher likelihood of being chosen by the scheduler over lower-priority threads. However, the actual execution order depends on the operating system's specific scheduling policy and the current load.

2. Why are priorities only hints for the scheduler? 

    Priorities are only hints because thread scheduling is ultimately controlled by the underlying operating system and its kernel. Java's thread priority levels are mapped to the OS's priority levels, but the OS may not honor the exact priority and can override the hint to ensure fairness, prevent starvation (where a low-priority thread never gets to run), or manage system resources.

## Part 3 - Coordination vs Synchronisation

### Task 3.1 Questions

1. Explain the difference between:
     - Synchronisation (e.g., using synchronized) - Coordination (e.g., using wait()/notifyAll())

| Concept | Primary Goal | Mechanism | When to Use |
|---|---|---|---|
| Synchronisation | Mutual Exclusion (Ensuring data integrity) | Uses locks (monitors) to guarantee that only one thread can execute a critical section of code at a time (synchronized keyword). | When accessing and modifying shared resources to prevent race conditions (e.g., updating a shared count). |
| Coordination | Inter-thread Communication (Ordering actions) | Uses wait(), notify(), and notifyAll() to allow threads to wait for a specific condition to become true (e.g., buffer is empty) and signal other threads when the condition changes. | When threads depend on a specific state of a shared resource (e.g., Producer must wait if a buffer is full; Consumer must wait if a buffer is empty). |
    

2. Give an example of when synchronisation alone is NOT enough. Synchronisation alone is not enough in the Producer-Consumer pattern.

    - You use synchronized to ensure only one thread modifies the buffer at a time (data integrity).

    - However, if the Producer finds the buffer is full, it needs to wait until the Consumer empties an item, and if the Consumer finds the buffer is empty, it needs to wait until the Producer adds an item.

    - This "wait for a condition" requirement is a job for coordination using wait() and notifyAll(), not just synchronisation.

## Part 4 - Implementing Producer-Consumer in Java

### Task 4.3 Questions

1. What happens if you remove the wait() calls? 

    If the wait() calls are removed from the while loops in produce and consume, the threads will enter a busy-wait or spin-lock state.

    - If the buffer is full (count == items.length), the Producer thread will continuously spin inside the while loop, hogging CPU resources while doing no useful work.

    - If the buffer is empty (count == 0), the Consumer thread will also spin inside the while loop, wasting CPU cycles.

    - This is inefficient and defeats the purpose of coordinated concurrency.

2. What happens if you remove notifyAll()? 

    If notifyAll() is removed, threads that are waiting (via wait()) will never be woken up.

    - When the Producer fills the buffer, any waiting Consumers will remain sleeping forever.

    - When the Consumer empties the buffer, any waiting Producers will remain sleeping forever.

    - The entire system will eventually deadlock or hang once the buffer becomes completely full or completely empty.

3. Why must wait() and notifyAll() be inside synchronised blocks?

    wait(), notify(), and notifyAll() must be called within a synchronized block (or method) because they require the calling thread to own the monitor (lock) of the object they are called on.

    - Mechanism: When wait() is called, the thread releases the lock and enters a waiting state. When notifyAll() is called, the thread must hold the lock to safely signal the waiting threads.

    - Safety: The compiler enforces this rule; calling these methods without holding the lock on the object will result in an IllegalMonitorStateException. This ensures that the state (the condition the threads are waiting for, like count == 0 or count == items.length) is checked and changed in a thread-safe manner.