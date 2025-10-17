# CO3408 – Week 2 Lab: Formal Specification & Contracts

## **Task 1**: BankAccount – Deposit and Withdraw Methods
Scenario: Implement a BankAccount class with deposit and withdraw methods.

### Requirements:
 - deposit(amount) increases balance by amount.
 - withdraw(amount) decreases balance by amount if sufficient funds exist.

### Instructions:
1. Write preconditions and postconditions for both methods.
2. Use Design by Contract style (JML-style comments or pseudo-code).

```java
public class BankAccount {

    private int balance;

    /*@ 
      @ ensures balance == 0;
      @*/
    public BankAccount() {
        balance = 0;
    }

    /*@ 
      @ requires amount > 0;                      // Precondition: deposit amount must be positive
      @ ensures balance == \old(balance) + amount; // Postcondition: new balance = old balance + amount
      @*/
    public void deposit(int amount) {
        balance += amount;
    }

    /*@ 
      @ requires amount > 0;                      // Precondition: withdraw amount must be positive
      @ requires balance >= amount;               // Precondition: must have enough funds
      @ ensures balance == \old(balance) - amount; // Postcondition: new balance = old balance - amount
      @*/
    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }
}
```

### Explanation of Preconditions and Postconditions
1. deposit(amount)
    - Precondition (requires)
        
        amount > 0: You can only deposit a positive amount.

    - Postcondition (ensures)

        balance == \old(balance) + amount: After deposit, balance increases by that amount.

2. withdraw(amount)
    - Precondition (requires)
        - amount > 0: You can only withdraw a positive amount.
        - balance >= amount: You can’t withdraw more than what you have.
    - Postcondition (ensures):
        - balance == \old(balance) - amount: After withdrawal, balance decreases by that amount.


## **Task 2**: Class Invariants

### Scenario: 
Use the BankAccount from Task 1.

### Instructions:
1. Define a class invariant to ensure the balance is never negative.
2. Explain why maintaining this invariant is important.

### Hint:

//@ invariant balance >= 0;

```java
public class BankAccount {
    private int balance;

    //@ invariant balance >= 0;  // Class invariant: balance can never be negative

    /*@ 
      @ ensures balance == 0;
      @*/
    public BankAccount() {
        balance = 0;
    }

    /*@ 
      @ requires amount > 0;
      @ ensures balance == \old(balance) + amount;
      @*/
    public void deposit(int amount) {
        balance += amount;
    }

    /*@ 
      @ requires amount > 0;
      @ requires balance >= amount;
      @ ensures balance == \old(balance) - amount;
      @*/
    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }
}

```

*Class Invariant Explanation*

Invariant:

```java
//@ invariant balance >= 0;
```


Meaning:

This invariant expresses that the balance must never be negative, no matter what operations (deposit or withdraw) are performed.

Why it’s important:

✅ Preserves logical consistency:
A negative balance would represent an invalid or impossible state for a typical bank account.

🛡️ Ensures contract safety:
Even if all methods meet their individual preconditions and postconditions, the invariant ensures that the overall object remains valid after every public method call.

💰 Reflects real-world constraints:
In most banking systems, customers can’t withdraw more money than they have (no overdraft). The invariant enforces that rule in the software model.

⚙️ Detects programming errors early:
If any method allows the balance to go below zero, the invariant is violated—helping developers quickly find and fix logic errors.


## **Task 3**: MinMax Class Specification

### Scenario: 
Implement a MinMax class to find the minimum and maximum of an array.

### Instructions:
1. Specify preconditions: 
    - array must not be empty.
2. Specify postconditions:
    - min returns the smallest element.
    - max returns the largest element.

3. Optionally, define a class invariant if applicable.

```java
public class MinMax {

    private int[] array;

    //@ invariant array != null && array.length > 0; // Class invariant: array must always be non-empty

    /*@
      @ requires arr != null;                     // Precondition: array must not be null
      @ requires arr.length > 0;                  // Precondition: array must not be empty
      @ ensures array == arr;                     // Postcondition: array field stores reference to arr
      @*/
    public MinMax(int[] arr) {
        this.array = arr;
    }

    /*@
      @ requires array.length > 0;                // Precondition: array must not be empty
      @ ensures (\forall int i; 0 <= i && i < array.length; \result <= array[i]); // Postcondition: result is the smallest element
      @*/
    public int getMin() {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min)
                min = array[i];
        }
        return min;
    }

    /*@
      @ requires array.length > 0;                // Precondition: array must not be empty
      @ ensures (\forall int i; 0 <= i && i < array.length; \result >= array[i]); // Postcondition: result is the largest element
      @*/
    public int getMax() {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max)
                max = array[i];
        }
        return max;
    }
}

```

🧩 Explanation

- Class Invariant:
    - Ensures the object always holds a valid array (array != null && array.length > 0).
- Preconditions:
    - Array cannot be null or empty.
    - getMin() and getMax() can only be called when this condition is true.
- Postconditions:
    - getMin() returns the smallest value.
    - getMax() returns the largest value.

### Scenario:

You are modelling a simple Bank ATM system that allows customers to perform 
standard banking operations. The system’s behaviour depends on its current state
and user actions (events).

### Instructions

#### *Step 1* – Gather System Requirements

List the main operations the ATM can perform:
- Insert Card
- Enter PIN
- Withdraw
- Deposit
- Eject Card

(Refer to the system requirements document for operation descriptions.)

#### *Step 2* – Identify Main System States

Determine the key system states involved in ATM operation:
- Idle – waiting for card
- Card Inserted – card detected, awaiting PIN
- PIN Entered – authenticated user session active
- Transaction in Progress – performing a withdrawal or deposit
- Out of Service – ATM unavailable due to fault or maintenance

#### *Step 3* – Draw the State Transition Diagram

Create a UML state diagram that shows:
- States as rounded rectangles (nodes)
- Transitions as labelled arrows representing events or operations

Your diagram should include (at minimum):
- InsertCard → moves Idle → Card Inserted
- EnterPIN → moves Card Inserted → PIN Entered
- Withdraw or Deposit → moves PIN Entered → Transaction in Progress
- EjectCard → returns system to Idle
- Error → moves Transaction in Progress → Out of Service
- MaintenanceDone → moves Out of Service → Idle

(Hint: use start/end symbols to mark system start in “Idle” and termination if 
required.)

![ATM_StateMachineDiagram](../out/Lab2_Week2/uml/ATM_StateMachineDiagram.png)

#### *Step 4* – Specify Preconditions and Postconditions

Choose one operation and formally express its precondition and postcondition to 
show how it affects system state.

Example:

- Operation: WithdrawCash
- Precondition: balance >= amount && currentState == PINEntered
- Postcondition: balance = old(balance) - amount && currentState = Idle

```java
/*@
  @ requires currentState == CardInserted;           // ATM must be in 'Card Inserted' state
  @ requires enteredPIN != null;                     // A PIN must be entered
  @ requires enteredPIN.length() == 4;               // Assuming a 4-digit PIN format
  @
  @ // Correct PIN case
  @ ensures (enteredPIN.equals(correctPIN)) ==> 
  @          (currentState == PINEntered && authSuccess == true);
  @
  @ // Incorrect PIN case
  @ ensures (!enteredPIN.equals(correctPIN)) ==> 
  @          (authAttempts == \old(authAttempts) + 1 &&
  @           currentState == CardInserted &&
  @           authSuccess == false);
  @
  @ // If 3 attempts are exceeded, card is ejected
  @ ensures (authAttempts >= 3) ==> 
  @          (currentState == Idle && cardEjected == true);
  @*/
public void EnterPIN(String enteredPIN) {
    // Implementation idea:
    if (enteredPIN.equals(correctPIN)) {
        authSuccess = true;
        currentState = State.PINEntered;
    } else {
        authAttempts++;
        authSuccess = false;
        if (authAttempts >= 3) {
            cardEjected = true;
            currentState = State.Idle;
        }
    }
}

```

🧩 Explanation

| Type | Condition | Meaning |
| ----------- | ----------- | ----------- |
| Precondition | ``currentState == CardInserted`` | The ATM must be waiting for PIN input.
| Precondition | ``enteredPIN != null && enteredPIN.length() == 4`` | A valid (non-null, 4-digit) PIN must be provided. |
| Postcondition (correct PIN) | ``currentState == PINEntered`` | System transitions to authenticated state. |
| Postcondition (incorrect PIN) | ``authAttempts`` increments by 1, remains in ``CardInserted`` | User can retry until 3 attempts reached. |
| Postcondition (max attempts) | ``authAttempts >= 3 ⇒ cardEjected == true && currentState == Idle`` | Card is ejected, ATM resets. |

💡 State Transitions (summary)

| From State | Operation | Condition | To State |
| ----------- | ----------- | ----------- | ----------- |
| ``Card Inserted`` | ``EnterPIN`` | PIN correct | ``PIN Entered`` |
| ``Card Inserted`` | ``EnterPIN`` | PIN incorrect, attempts < 3 | ``Card Inserted`` (retry) |
| ``Card Inserted`` | ``EnterPIN`` | attempts ≥ 3 | ``Idle`` (card ejected) |