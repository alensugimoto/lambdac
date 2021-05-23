package ch.usi.pf2.tui;

import ch.usi.pf2.model.Interpreter;

import java.util.Scanner;


/**
 * The main class of the textual user interface of this application.
 */
public class TextualUserInterface {
    
    private final Interpreter interpreter;
    
    /**
     * Constructs a new TextualUserInterface for the given interpreter.
     * @param interpreter the model to show
     */
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
                System.out.println(interpreter.interpret(input));
            }
        }
        scanner.close();
    }
    
    private void printWelcome() {
        System.out.println(interpreter.getWelcomeMessage());
    }
    
    private void printHelp() {
        System.out.println("Type \"exit\" to exit.");
    }
    
}
