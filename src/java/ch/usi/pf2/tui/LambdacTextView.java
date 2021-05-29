package ch.usi.pf2.tui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.LambdacModelListener;
import ch.usi.pf2.model.parser.ParseException;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Scanner;


/**
 * The main class of the textual user interface of this application.
 */
public class LambdacTextView {
    
    private final LambdacModel model;
    
    /**
     * Constructs a new TextualUserInterface for the given interpreter.
     * @param interpreter the model to show
     */
    public LambdacTextView(final LambdacModel model) {
        this.model = model;

        // register listeners
        model.addPropertyChangeListener(new LambdacModelListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName()
                       .equals(LambdacModel.TEXT_TO_INTERPRET_PROPERTY)) {
                    try {
                        model.interpret();
                    } catch (ParseException ex) {
                        System.err.println(ex.getMessage());
                    }
                } else if (evt.getPropertyName()
                              .equals(LambdacModel.INTERPRETED_TEXT_PROPERTY)) {
                    System.out.println(evt.getNewValue());
                } else {
                    try {
                        model.open();
                    } catch (IOException ex) {
                        System.err.println("A problem was encountered");
                    }
                }
            }
            
        });
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
                runText(input);
            }
        }
        scanner.close();
    }

    public void runText(final String textToInterpret) {
        model.setTextToInterpret(textToInterpret);
    }

    public void runFile(final String fileName) {
        model.setFileName(fileName);
    }
    
    private void printWelcome() {
        System.out.println(LambdacModel.NAME + "\nType \"help\" for more information.");
    }
    
    private void printHelp() {
        System.out.println("Type \"exit\" to exit.");
    }
    
}
