package ch.usi.pf2.tui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.parser.ParseException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Scanner;

/**
 * The main class of the textual user interface of this application.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class LambdacTextView {

    private static final String EXIT_COMMAND = "exit";
    private static final String HELP_COMMAND = "help";
    
    private final LambdacModel model;
    
    /**
     * Constructs a new TextualUserInterface for the specified model.
     * @param model the model to use
     */
    public LambdacTextView(final LambdacModel model) {
        this.model = model;
        model.addPropertyChangeListener(new LambdacModelListener());
    }

    /**
     * Runs this textual user interface with a REPL.
     */
    public final void run() {
        printWelcome();
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String input = scanner.nextLine();
            if (input.equals(EXIT_COMMAND)) {
                break;
            } else if (input.equals(HELP_COMMAND)) {
                printHelp();
            } else {
                runText(input);
            }
        }
        scanner.close();
    }

    /**
     * Interprets the specified text.
     * @param textToInterpret the text to be interpreted
     */
    public final void runText(final String textToInterpret) {
        model.setTextToInterpret(textToInterpret);
    }

    /**
     * Interprets the file with the specifed file name.
     * @param fileName the name of the file to be interpreted
     */
    public final void runFile(final String fileName) {
        model.setFileName(fileName);
    }
    
    private final void printWelcome() {
        System.out.format("%s (%s)%n", LambdacModel.NAME, LambdacModel.VERSION);
        System.out.format("Type '%s' for more information.%n", HELP_COMMAND);
        System.out.format("Type '%s' to exit.%n", EXIT_COMMAND);
    }
    
    private final void printHelp() {
        System.out.println(LambdacModel.getHelp());
    }

    private final class LambdacModelListener implements PropertyChangeListener {

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(LambdacModel.TEXT_TO_INTERPRET_PROPERTY)) {
                try {
                    model.interpret();
                } catch (ParseException ex) {
                    System.err.println(ex.getMessage());
                }
            } else if (evt.getPropertyName().equals(LambdacModel.INTERPRETED_TEXT_PROPERTY)) {
                System.out.println(evt.getNewValue());
            } else {
                try {
                    model.open();
                } catch (IOException ex) {
                    System.err.format("A problem was encountered reading the file named '%s'%n",
                                      evt.getNewValue());
                }
            }
        }

    }
    
}
