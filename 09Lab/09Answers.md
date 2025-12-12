# Lab 08 - Deadlocks

## Task 1: Identify and Reproduce a Deadlock

### Deadlock Explanation

Deadlock occurs because all four necessary conditions are met:

- Mutual Exclusion: Both resources (A and B) are non-sharable and can only be held by one thread at a time (enforced by synchronized blocks).

- Hold and Wait: Thread t1 holds resource A and is waiting for B. Thread t2 holds resource B and is waiting for A.

- No Preemption: A resource (lock) cannot be forcibly taken from a thread; it must be released voluntarily.

- Circular Wait: t1 waits for t2's resource (B), and t2 waits for t1's resource (A), creating a circular dependency.


### Key Concept: Deadlock

A deadlock is a condition where two or more threads are perpetually waiting for each other to release a resource, resulting in a standstill where no thread can procee.

### Explanation of `DeadlockDemo.java`

The program uses two shared resources, `A` and `B`, which act as locks (`Object` instances used with `synchronized`). The deadlock arises because the two threads, $t1$ and $t2$, acquire these locks in **different orders**.

  * **Thread $t1$** acquires lock $A$, and then tries to acquire lock $B$.
  * **Thread $t2$** acquires lock $B$, and then tries to acquire lock $A$.

The `sleep(100)` call is strategically placed after the first lock acquisition to increase the likelihood of the two threads grabbing their first locks simultaneously.

**Deadlock Sequence:**

1.  $t1$ acquires $A$.
2.  $t2$ acquires $B$.
3.  $t1$ pauses for 100ms while holding $A$.
4.  $t2$ pauses for 100ms while holding $B$.
5.  $t1$ attempts to acquire $B$, but $B$ is held by $t2$. $t1$ waits.
6.  $t2$ attempts to acquire $A$, but $A$ is held by $t1$. $t2$ waits.

Since $t1$ is waiting for $t2$ to release $B$, and $t2$ is waiting for $t1$ to release $A$, neither can ever proceed, causing the program to hang.

## Task 2: Fix the Deadlock Using Resource Ordering

This task demonstrates a standard technique for preventing deadlocks: **Resource Ordering**.

### Key Concept: Resource Ordering

Resource ordering (or lock hierarchy) breaks the **Circular Wait** condition, one of the four necessary conditions for deadlock. By establishing a fixed, global order for acquiring resources, it's impossible for Thread A to wait for Thread B's resource while Thread B waits for Thread A's resource, as both must follow the same sequence.

### Explanation of `OrderedLocking.java`

In the corrected code, both threads are required to acquire the locks in the exact same sequence: **$A$ then $B$** (alphabetical order).

```java
// Same order for all threads: A then B
synchronized (A) { 
    synchronized (B) { 
        // ... critical section ...
    }
}
```

Since both $t1$ and $t2$ must acquire $A$ before $B$, only one thread can successfully acquire $A$ at a time. The thread that acquires $A$ first will eventually acquire $B$, finish its work, and release both locks, allowing the other thread to proceed. This ensures the program **never deadlocks**.

## Task 3: Simple Reader–Writer Problem Using ReadWriteLock

This task addresses a common concurrency challenge where different types of access (reading vs. writing) require different synchronization rules. Simple `synchronized` locks are too restrictive here.

### Key Concept: ReadWriteLock

The `java.util.concurrent.locks.ReadWriteLock` library is designed for this pattern. It manages two separate locks:

1.  **Read Lock (`lock.readLock().lock()`):** Allows multiple threads to hold it simultaneously. This enables **multiple readers** to read concurrently.
2.  **Write Lock (`lock.writeLock().lock()`):** Allows only a **single thread** to hold it at a time. Furthermore, it *blocks* if any read locks or other write locks are currently held. This ensures **exclusive access** for writing.

### Explanation of `ReaderWriterDemo.java`

  * **Reader threads** acquire the `readLock`. The $R-W$ Lock implementation permits any number of readers to hold the lock at the same time, promoting high performance for read-heavy workloads.
  * **Writer threads** acquire the `writeLock`. The $R-W$ Lock implementation ensures that if a writer holds the lock, no readers or other writers can acquire their respective locks until the writer is finished.

This structure correctly implements the required constraints: multiple simultaneous readers, but only one writer at a time, and no writer while a reader is present.