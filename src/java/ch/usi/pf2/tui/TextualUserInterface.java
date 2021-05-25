package ch.usi.pf2.tui;

import ch.usi.pf2.model.Interpreter;
import ch.usi.pf2.model.parser.ParseException;

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
                try {
                    System.out.println(interpreter.interpret(input));
                } catch (ParseException ex) {
                    String pointer = "";
                    for (int i = 0; i < ex.getPosition(); i++) {
                        pointer += " ";
                    }
                    System.out.println(pointer + "^\n" + ex.getMessage());
                }
            }
        }
        scanner.close();
    }
    
    private void printWelcome() {
        System.out.println(Interpreter.NAME + "\nType \"help\" for more information.");
    }
    
    private void printHelp() {
        System.out.println("Type \"exit\" to exit.");
    }
    
}
