# Tutorial: Using Sets, Sequences, and Recursion in Modeling

## Example 1 – Working with Sets

Let’s model a simple **Login System**.

```java
ValidUsers = {Alice, Bob, Charlie}
LoggedInUsers = {}
````

### Scenario

* Alice logs in.

So:

```java
LoggedInUsers = {Alice}
```

### Questions & Answers

**Q1:** What happens when Alice logs in?

**A1:** Alice is added to the `LoggedInUsers` set.

→ `LoggedInUsers = {Alice}`

---

**Q2:** How can we ensure no one logs in who isn’t in `ValidUsers`?

**A2:** Before allowing a login, we check:

```java
if (user ∈ ValidUsers) {
    LoggedInUsers = LoggedInUsers ∪ {user};
}
```

This ensures that **only valid users** can be added.

---

**Q3:** What mathematical relationship ensures this rule?

**A3:**

```java
LoggedInUsers ⊆ ValidUsers
```

This means *every logged-in user must be a valid user.*

---

**Q4:** Why is this called an **invariant**?

**A4:** An **invariant** is a condition that must always hold true throughout the system’s operation.
Even if users log in or out, it should always remain true that:

> `LoggedInUsers ⊆ ValidUsers`

---

## Example 2 – Sequences

Now let’s look at **ordered data**, using a sequence of transactions.

```java
Transactions = [+100, -50, +25]
```

### Questions & Answers

**Q1:** How do we find the total balance?

**A1:**
Add all elements in order:

```java
Total = 100 - 50 + 25 = 75
```

---

**Q2:** How does order matter here (sequence vs. set)?

**A2:**

* In a **sequence**, **order matters**: `[+100, -50, +25]` is **not the same** as `[-50, +100, +25]`.
* In a **set**, order does **not** matter: `{+100, -50, +25}` is the same as `{+25, +100, -50}`.

So, for **financial transactions**, we need a sequence because the **order of deposits and withdrawals** changes the final balance.

---

**Q3:** What happens if we remove duplicates?

**A3:**
If we convert the sequence to a set, duplicates disappear and **the meaning changes**.
Example:

```java
Transactions = [+100, -50, +25, -50]
As a set → {+100, -50, +25}
```

This would give the wrong total.
Hence, **sequences preserve meaning through order and repetition.**

---

## Example 3 – Recursion

Let’s explore recursion with a simple **factorial** method.

```java
public int factorial(int n) {
    if (n == 0) return 1;
    else return n * factorial(n - 1);
}
```

### Questions & Answers

**Q1:** What’s the **base case**?

**A1:**
The condition that stops the recursion:

```java
if (n == 0) return 1;
```

---

**Q2:** What’s the **recursive case**?

**A2:**
The part that calls the function again:

```java
return n * factorial(n - 1);
```

---

**Q3:** Why must we stop recursion?

**A3:**
Without a stopping condition, recursion would **never end** → leading to **infinite calls** or a **stack overflow**.
The **base case** ensures termination.

---

**Q4:** How could we express a precondition?

**A4:**
We can use **JML (Java Modeling Language)** to specify conditions that must hold before execution:

```java
//@ requires n >= 0;
```

This means the method only accepts non-negative integers.

---

## Example 4 – Connecting to Modeling

Now, let’s see how **sets**, **sequences**, and **recursion** connect.

| Concept       | Example in Login System                       | Role                             |
| ------------- | --------------------------------------------- | -------------------------------- |
| **Set**       | `{Alice, Bob, Charlie}`                       | Represents valid users           |
| **Sequence**  | `[Login(Alice), Logout(Bob), Login(Charlie)]` | Represents ordered log of events |
| **Recursion** | `processLog(log)`                             | Processes each event one by one  |

### Pseudo-code

```java
processLog(sequence log) {
    if (log is empty) return;
    else {
        handle(log.first);
        processLog(log.rest);
    }
}
```

This combines:

* **Sets** → define valid states
* **Sequences** → capture event order
* **Recursion** → process data step by step

---

## Further Reading

* [JML Tutorial – Reasoning about recursive functions and data structures](https://www.openjml.org/tutorial/Recursion?utm_source=chatgpt.com)
* [JML Tutorial – Reasoning about recursive functions and data structures (model method version)](https://www.openjml.org/tutorial/Recursion-MM?utm_source=chatgpt.com)

---


