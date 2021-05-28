package ch.usi.pf2.tui;

import ch.usi.pf2.model.LambdaFileListener;
import ch.usi.pf2.model.LambdaTextEditor;
import ch.usi.pf2.model.LambdaTextListener;

import java.io.IOException;
import java.util.Scanner;


/**
 * The main class of the textual user interface of this application.
 */
public class LambdaTextEditorTextView {
    
    private final LambdaTextEditor textEditor;
    
    /**
     * Constructs a new TextualUserInterface for the given interpreter.
     * @param interpreter the model to show
     */
    public LambdaTextEditorTextView(final LambdaTextEditor textEditor) {
        this.textEditor = textEditor;

        textEditor.getText().addLambdaTextListener(new LambdaTextListener() {
            
            @Override
            public void textToInterpretChanged(final String textToInterpret) {
                textEditor.getText().interpret();
            }

            @Override
            public void interpretedTextChanged(final String interpretedText) {
                System.out.println(interpretedText);
            }
            
        });
        textEditor.getFile().getText().addLambdaTextListener(new LambdaTextListener() {

            @Override
            public void textToInterpretChanged(final String textToInterpret) {
                textEditor.getFile().getText().interpret();
            }

            @Override
            public void interpretedTextChanged(final String interpretedText) {
                System.out.println(interpretedText);
            }
            
        });
        textEditor.getFile().addLambdaFileListener(new LambdaFileListener() {
            
            @Override
            public void fileNameChanged(final String fileName) {
                try {
                    textEditor.getFile().open();
                } catch (IOException ex) {
                    System.err.println("A problem was encountered reading " + fileName);
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
        textEditor.getText().setTextToInterpret(textToInterpret);
    }

    public void runFile(final String fileName) {
        textEditor.getFile().setName(fileName);
    }
    
    private void printWelcome() {
        System.out.println(textEditor.getName() + "\nType \"help\" for more information.");
    }
    
    private void printHelp() {
        System.out.println("Type \"exit\" to exit.");
    }
    
}
