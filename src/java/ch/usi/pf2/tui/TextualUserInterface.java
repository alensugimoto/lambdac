package ch.usi.pf2.tui;

import ch.usi.pf2.model.Interpreter;
import ch.usi.pf2.model.parser.ParseException;

import java.util.Scanner;


/**
 * Write a description of class TextualUserInterface here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextualUserInterface {
    
    private final Interpreter interpreter;
    
    public TextualUserInterface(final Interpreter interpreter) {
        this.interpreter = interpreter;
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
                try {
                    System.out.println(interpreter.interpret(input));
                } catch (ParseException ex) {
                    System.err.println(ex.getMessage());
                }
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
