# Lambdac

The purpose of this application is to provide user interfaces for interpreting source code written in untyped lambda calculus.

## How to Build

To build, use

```
mvn package
```

## How to Run

To run, use

```
java -cp target/project-lambdac-0.1-SNAPSHOT.jar Main
```

## User Instructions

With the application running, type any lambda term and press enter to evaluate it.

The syntax of a lambda term must follow the following BNF:

```
TERM        ::= ATOM
              | ABSTRACTION
              | APPLICATION
ATOM        ::= Identifier
              | "(" TERM ")"
ABSTRACTION ::= "\" Identifier "." TERM
APPLICATION ::= ATOM ATOM
              | APPLICATION ATOM
```

Note that the interpreter evaluates input strings to a value/abstraction; therefore, all variables must be defined.

## Authors

* Arthur Morgan
* Alen Sugimoto

