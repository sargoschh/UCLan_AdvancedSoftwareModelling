# CO3408 – Week 13 Lab: Formal Grammars and Syntax Structure

## Task 1: Writing a Simple CFG

Define a context-free grammar (CFG) in BNF for arithmetic expressions using:

- Addition (+)
- Multiplication (*)
- Parentheses
- Identifiers (a, b, c …)

```` 
<expr>   ::= <expr> "+" <term> | <term>
<term>   ::= <term> "*" <factor> | <factor>
<factor> ::= "(" <expr> ")" | <id>
<id>     ::= "a" | "b" | "c"
````

## Task 2: Derivation of a String

Using the CFG from Task 1, derive the string: (a + b) * c

````
<expr>

<term> (using <expr> ::= <term>)

<term> * <factor>

<factor> * <factor>

( <expr> ) * <factor>

( <expr> + <term> ) * <factor>

( <term> + <term> ) * <factor>

( <factor> + <term> ) * <factor>

( a + <term> ) * <factor>

( a + <factor> ) * <factor>

( a + b ) * <factor>

( a + b ) * c
````

## Task 3: Draw a Parse Tree

Draw the parse tree for (a + b) * c using the CFG from Task 1.
- Leaves → terminals (tokens)
- Internal nodes → non-terminals
- Root → start symbol (<Expr>)

![alt text](Task3.png)

## Task 4: Ambiguity in Grammar

Given Grammar: E ::= E + E | E * E | IDENT
1. Show that the string a + b * c has more than one parse tree
![alt text](<Top down parsing.jpg>)
2. Explain why this grammar is ambiguous

    A grammar is ambiguous if a single string can result in more than one distinct parse tree. For the grammar ````E ::= E + E | E * E | IDENT````, the string ````a + b * c```` can be interpreted in two ways:

    - Tree A (Addition First): This groups the expression as (a + b) * c.

    - Tree B (Multiplication First): This groups the expression as a + (b * c).

    This grammar is ambiguous because it lacks precedence and associativity rules.

    - It treats + and * as having the same "rank," allowing the compiler to pick either operation to be the parent node.

    - In computer science, we want our languages to be unambiguous so that 2 + 3 * 4 always equals 14, never 20. The grammar in Task 1 solves this by forcing * to be deeper in the tree (evaluated first) than +.

