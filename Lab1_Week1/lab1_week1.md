# CO3408: Advanced Software Modelling
## Lab Exercise: From Informal to Formal Specifications
### Learning Objectives
By the end of this lab, you should be able to:
- Identify problems in informal specifications (ambiguity, incompleteness, inconsistency).
- Write informal preconditions and postconditions for simple programs.
- Translate informal specifications into formal contracts (pre/post conditions).
- Begin expressing specifications in JML (Java Modeling Language).

### Part 1 – Spot the Issues in Informal Specifications
Read the following informal specifications. For each one, identify at least two possible issues (ambiguity, incompleteness, inconsistency, error conditions).
1.	“A program to calculate the square root of a number.”
    - *Ambiguous*: What type of number is expected (integer, float, etc.)? How exact do the calculations need to be?
    - *Error Cases*: What if the input is negative or the input is not a number?
    - *Unclear Assumptions*: Is the input always non-negative and does the input needs to be validated?
2.	“A program to find the area of a triangle given 3 sides.” 
    - *Ambiguous*: Which formula is used (e.g., Pythagoras or Herons Formula)? Are all side combinations valid?
    - *Error Cases*: What if the sides do not form a valid triangle? What if one or more sides are zero or negative?
    - *Unclear Assumptions*: Are the sides always positive numbers and is input validation required?
3.	“A program to word-wrap text.” 
    - *Ambiguous*: What defines the wrap limit—character count or screen width? How are long words handled?
    - *Error Cases*: What if the input is empty? What if the input contains special characters?
    - *Unclear Assumptions*: Is the input always a string? Is there a maximum line length?
4.	“A program to act as a calculator with a good user interface.” 
    - *Ambiguous*: What defines a “good” user interface? What operations should the calculator support?
    - *Error Cases*: What happens with division by zero? What if the user inputs invalid expressions?
    - *Unclear Assumptions*: Who is the target user? What input methods are supported (keyboard, touch, etc.)?
5.	“A program to find where the largest number is in an array.”
    - *Ambiguous*: What if the largest number appears multiple times? Should the program return the first occurrence or all?
    - *Error Cases*: What if the array is empty? What if the array contains non-numeric values?
    - *Unclear Assumptions*: Are negative numbers allowed? Is the array always numeric?

### Part 2 – Write Informal Contracts
For each problem above, try to add informal preconditions and postconditions. 

**Example:**
```java
// Informal contract for square root
// Requires: input number ≥ 0
// Ensures: result * result ≈ input number
```

Task: Write similar informal contracts for:

- Triangle area
```java
// Calculates area for Triangles with herons’ formula
// Requires: variables a & b & c > 0
// Ensures: area of triangle calculated by heron´s formula -> area > 0
```
- Largest number in array
```java
// gives back the largest number of an array with whole numbers
// Requires: array containing whole numbers 
// Ensures: the largest number is returned to the caller
```

### Part 3 – Move Towards Formality
Take the function below:
```java
int findPosition(int target, int[] items)
```

Task: Write informal:
- Requires: target number (int) and array [int]
- Ensures: Returns index of the given number in the array

Then, discuss in small groups:
- What if the array is empty?
    - Return -1 or throw an Exception
- What if target is not present?
    - Return -1 or throw an Exception
- What if target appears more than once?
    - Return first appearance of target

### Part 4 – Formalise in JML
Convert one of your informal contracts into JML.
**Example:**
```java
/*@ requires val >= 0;
  @ ensures \abs((\result * \result) - val) < 0.0001;
  @*/
public double squareRoot(double val) { ... }
```

Task: Write a JML specification for findPosition.
```java
/*@
requires items != null && items.length > 0;
ensures (\exists int i; 0 <= i && i < items.length; items[i] == target ==> \result == i);
ensures (\forall int i; 0 <= i && i < items.length; items[i] != target ==> \result == -1);
@*/
public int findPosition(int target, int[] items) { ... }
```
### Part 5 – Reflection
In a short paragraph (150 words max):	

For me the JML part was the hardest because I never really used JML before. It took time to understand the syntax and how to express conditions formally. However, once I got used to it, I realised that formalisation helped clarify the expected behaviour of the program. It made me think more carefully about edge cases and what the function should return in different situations. The biggest benefit of formal specifications is that they reduce misunderstandings between developers and make software more reliable. On the other hand, the biggest drawback is that writing formal specifications can be time-consuming and difficult for beginners. It requires a good understanding of logic and the specification language, which can be challenging at first.
