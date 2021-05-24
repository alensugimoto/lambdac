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
java -cp target/project-lambdac-0.1-SNAPSHOT.jar ch.usi.pf2.Main
```

The following command line arguments are also supported, which can be appended to the above command to use:

* `--gui` opens the graphical user interface instead of the default textual user interface
* `--help` shows more information about the application (work in progress)
* `<filename>` interprets the content of a file and prints the result (work in progress)
* `-c <text>` interprets the given text and prints the result (work in progress)

## User Instructions

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

## Author

* Alen Sugimoto
