# Lambdac

The purpose of this application is to provide user interfaces for interpreting source code written in untyped lambda calculus. It contains an interpreter that evaluates lambda expressions using the call-by-value evaluation strategy.

This application, named Lambdac, is written in Java. It currently can run as either a textual user interface or a graphical user interface, both of which can run files and text. The implementation is based on the book [Types and Programming Languages](https://www.cis.upenn.edu/~bcpierce/tapl/).

## How to Build

To build, first clone this repository and then, inside it, run

```
mvn package
```

## How to Run

To start the application after the build, run

```
java -cp target/project-lambdac-0.1-SNAPSHOT.jar ch.usi.pf2.Main
```

The following command line arguments are also supported, which can be appended to the above command:

* `--gui` opens the graphical user interface instead of the default textual user interface
* `--help` shows more information about the application
* `<filePath>` interprets the content of a file and prints the result
* `-c <text>` interprets the given text and prints the result

## User Instructions

The lambda expressions that are written in this application must follow the following grammar:

```
TERM        ::= ATOM
              | ABSTRACTION
              | APPLICATION
ATOM        ::= IDENTIFIER
              | "(" TERM ")"
ABSTRACTION ::= "\" IDENTIFIER "." TERM
APPLICATION ::= ATOM ATOM
              | APPLICATION ATOM
IDENTIFIER  ::= [a-zA-Z_][a-zA-Z_0-9]*
```

This means that an application is left associative (i.e. `x y z` is the same as `(x y) z`) and an abstraction extends as far to the right as possible (i.e. `\x.x x` is the same as `\x.(x x)` but not `(\x.x) x`).

### Examples

Examples of lambda expressions that follow the above grammar are shown below:

```
(\x.x x) (\y.y)
(\b.\c.b c (\t.\f.f)) (\t.\f.t) (\t.\f.f)              # and true false
(\l.\m.\n.l m n) (\t.\f.f) (\x.x) (\y.y)               # ifthenelse false (\x.x) (\y.y)
(\n.\s.\z.s (n s z)) (\s.\z.s z)                       # succ 1
(\m.\n.\s.\z.m s (n s z)) (\s.\z.s z) (\s.\z.s (s z))  # plus 1 2
```

## Authors

* Alen Sugimoto
