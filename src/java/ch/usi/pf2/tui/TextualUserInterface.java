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

    /**
     * Run the textual user interface.
     */
    public void run() {
        printWelcome();
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            } else if ("help".equals(input)) {
                printHelp();
            } else {
                System.out.println(interpreter.interpret(input));
            }
        }
        scanner.close();
    }
    
    private void printWelcome() {
        System.out.println("Lambdac");
        System.out.println("Type \"help\" for more information.");
    }
    
    private void printHelp() {
        System.out.println("Type \"exit\" to exit.");
    }
    
}
