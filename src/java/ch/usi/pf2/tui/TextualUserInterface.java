package ch.usi.pf2.tui;

import ch.usi.pf2.model.Interpreter;

import java.util.Scanner;


/**
 * Write a description of class TextualUserInterface here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextualUserInterface {
    
    private final Interpreter interpreter;
    
    public TextualUserInterface() {
        interpreter = new Interpreter();
    }

    public void run() {
        printWelcome();
        final Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!"quit".equals(input)) {
            System.out.println(interpreter.interpret(input));
            input = scanner.nextLine();
        }
        scanner.close();
    }
    
    private void printWelcome() {
        System.out.println("Welcome");
    }
    
}
