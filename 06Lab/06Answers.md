#  Lab 6: Concurrency and Synchronization

## Part (1): Introduction (Discussion) Answers

1. What is concurrency, and why is it needed in software systems?

    Concurrency is the ability of a system to handle multiple tasks or processes seemingly at the same time. It involves structuring a program such that multiple tasks can make progress over the same period of time, often by interleaving their execution.

    It's needed in software systems primarily for two reasons:

    - Responsiveness: Concurrency allows the user interface of an application to remain responsive while long-running tasks (like loading a file or making a network request) are performed in the background. Without it, the application would freeze, leading to a poor user experience.

    - Efficiency/Throughput: It enables systems to better utilize resources (like a single CPU core) by switching quickly between tasks that are waiting for I/O (input/output) operations to complete (e.g., waiting for data from a hard drive or the internet).

2. What are some examples of concurrent systems in everyday software?

    Common examples of concurrent systems include:

- Web Browsers: A browser can load multiple images, scripts, and stylesheets simultaneously while also allowing you to scroll or interact with the page.

- Operating Systems (OS): An OS manages dozens of applications and background processes (like the file system, network services, and device drivers) all running concurrently.

- Database Management Systems: A database server can process query requests from hundreds of different users at the same time.

- Multiplayer Video Games: The game engine handles rendering the graphics, processing player input, updating the game state (physics, AI), and communicating with the server all concurrently.

3. What is the difference between concurrency and parallelism?

    Concurrency is about dealing with many things at once. It's a structural property of the program, where multiple execution flows are managed, often by time-slicing on a single processing core. Think of a single chef juggling tasks: chopping vegetables, stirring a pot, and seasoning a dish.

    Parallelism is about doing many things at once. It's a runtime property where multiple tasks are executed simultaneously on different physical processing resources (multiple CPU cores or machines). Think of multiple chefs working in the same kitchen on different recipes at the exact same time.

## Part (2): Basic Thread Creation Questions

1. How do the outputs from both threads appear?

    The outputs from the two threads ("Thread-A" and "Thread-B") will appear interleaved. You will see print statements from both threads mixed together in the console output, as the operating system schedules them to run concurrently.

2. Do they always run in the same order?
Why or why not?

    No, they do not always run in the same order. The order in which the threads execute, and thus the order of their output, is controlled by the thread scheduler of the operating system. The scheduler's decisions are non-deterministic, meaning the timing and interleaving of thread execution can vary significantly each time the program is run.

## Part (3): Shared Resource Problem (Race Condition) Observation Questions

1. Is the final count correct (should be 10)?

    No, the final count is not guaranteed to be correct (10). Due to the race condition, the final value might be less than 10, though it will not exceed 10.

2. Why might it not be correct?

    It might not be correct because of unsynchronized access to the shared variable count (a race condition). The increment() operation (count++) is not atomic; it involves three separate machine operations:

    - Read the current value of count from memory.

    - Increment the value (add 1).

    - Write the new value back to memory.

    If Thread-1 reads the value (e.g., 5), and before it can write the incremented value (6) back, Thread-2 also reads the value (still 5), both threads will then write the value 6. This means an increment operation was effectively lost.

## Part (4): Synchronizing Access Observation & Discussion Questions

1. Is the output now consistent?

    Yes, the output is now consistent. The final count will reliably be 10 (since 5 increments from Thread-1+5 increments from Thread-2=10 total), and each increment will be applied uniquely, ensuring the count sequence is correct, even if the threads take turns.

2. What has changed?

    The use of the synchronized keyword on the increment() method has changed the behavior. It ensures that only one thread can execute that method (the critical section) at a time. The thread must first acquire the intrinsic lock (monitor) associated with the Counter object before proceeding.

3. What is a critical section?

    A critical section is a section of code that accesses a shared resource (like a variable, data structure, or file) and must not be executed concurrently by more than one thread. It requires synchronization to ensure data integrity and prevent race conditions.

4. What does the synchronized keyword do behind the scenes?

    When applied to an instance method, the synchronized keyword causes the executing thread to automatically acquire the intrinsic lock (monitor) of the object instance (this) upon entering the method and release the lock upon exiting the method (either normally or via an exception). This mechanism ensures mutual exclusion, meaning no other thread can acquire the same lock and execute another synchronized method on the same object until the current thread releases it.

5. Can synchronization affect performance?

    Yes, synchronization can affect performance.

    - Overhead: There is a slight overhead associated with acquiring and releasing the lock.

    - Contention: If many threads frequently compete for the same lock (high contention), they spend time waiting for the lock to be released, which can significantly reduce the potential speedup from concurrency.

## Part (5): Higher-Level Concurrency (Executors) Questions

1. How many threads are actually running?

    The code Executors.newFixedThreadPool(3) creates a pool with a maximum of 3 worker threads. Although the loop submits 5 tasks, only 3 threads from the pool will be running those tasks concurrently.

2. Why does the output order vary each time you run it?

    The output order varies because the tasks are submitted to a thread pool and processed by the available worker threads. The thread pool uses an internal queue to hold the tasks, and the operating system's thread scheduler still governs how the 3 worker threads execute their assigned tasks, leading to the same non-deterministic interleaving observed in Part 2.

## Part (6): Reflection (Short Answers)

1. What did you learn about concurrency in Java?

    Learned that Java uses threads to achieve concurrency, which allows multiple tasks to run interleaved or simultaneously. Also learned that concurrency can be managed using both the lower-level Thread class and the higher-level Executor Framework (thread pools).

2. What problems can occur without synchronization?

    Without synchronization when accessing shared resources, a race condition can occur, leading to inconsistent or incorrect data. This happens because one thread's operation on the shared data can be interrupted by another thread, causing lost updates or data corruption.