# CO3408 – Week 12 Lab: Regular Expressions & Lexical Analysis

## Task 1: Identifying Tokens and Lexemes

Given Input int total = 25;
1.	Identify the tokens present in the input.
2.	For each token, specify:
    1.	Token type
    2.	Lexeme

| Lexeme | Token Type |
| --- | --- |
| `int` | Keyword (Reserved Word) |
| `total` | Identifier |
| `=` | Assignment Operator |
| `25` | Integer Literal |
| `;` | Separator / Punctuation |

## Task 2: Writing Regular Expressions for Tokens

Write regular expressions for the following token types:
1.	Identifier
    * **Identifier:** `^[a-zA-Z_][a-zA-Z0-9_]*$` 
    * *Explanation:* Starts with a letter or underscore, followed by any number of letters, digits, or underscores.
2.	Integer number
    * **Integer number:** `^[0-9]+$` 
    * *Explanation:* One or more digits from 0–9.
3.	Whitespace
    * **Whitespace:** `\s+` 
    * *Explanation:* Matches one or more spaces, tabs, or line breaks.
4.	Arithmetic operator (+, -, *, /)
    * **Arithmetic operator:** `[+\-*/]` 
    * *Explanation:* Matches any one of the basic math symbols.

## Task 3: Lexical Validation of JSON Fields

``` json
{
  "username": "user_01",
  "age": 30,
  "isAdmin": false
}
```

Write regular expressions to validate the following JSON token types:

1.	**JSON string**
    * `"([^"\\]|\\.)*"` 
2.	**JSON number**
    * `-?(0|[1-9][0-9]*)(\.[0-9]+)?([eE][+-]?[0-9]+)?` 
3.	**Boolean literal**
    * `^(true|false)$` 
4.	**JSON field name (key)**
    * `^"[a-zA-Z_][a-zA-Z0-9_]*"$` 

## Task 4: Lexical Errors Detection

Identify whether each input contains a lexical error, and briefly explain why.
1. **`count = 12a;`**
    * **Error:** The lexeme `12a` is invalid.
    * **Reason:** Most languages do not allow identifiers to start with a digit, and "12a" does not match a standard integer or identifier pattern.
2. **`"name": "Alice`**
    * **Error:** Unterminated string literal.
    * **Reason:** Lexical analyzers expect a closing quote to complete a string token; without it, the lexer reaches the end of the line/file unexpectedly.
3. **`@total = 5;`**
    * **Error:** Illegal character `@`.
    * **Reason:** If the language specification does not define `@` as a valid operator or start of an identifier, it is flagged as an invalid character.

## Task 5: Lexical Analysis vs. Parsing

**Question:** Explain one reason why regular expressions alone are insufficient to validate full JSON documents.

**Answer:** Regular expressions (which represent **Finite Automata**) cannot handle **recursive or nested structures**. JSON allows for objects within objects and arrays within arrays to an infinite depth (e.g., `{"a": {"b": {"c": 1}}}`). Regular expressions cannot "count" or match balanced braces `{ }` at arbitrary depths. This requires a **Context-Free Grammar** and a parser (typically a Pushdown Automaton) rather than just a lexical analyzer.

