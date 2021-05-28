package ch.usi.pf2;

import ch.usi.pf2.gui.LambdaTextEditorFrame;
import ch.usi.pf2.model.LambdaTextEditor;
import ch.usi.pf2.tui.LambdaTextEditorTextView;


/**
 * The class containing the application's main() method.
 */
public final class Main {

    private Main() {
    }
    
    /**
     * Creates the model and then either the textual or graphical
     * user interface based on the specified arguments.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        final LambdaTextEditor textEditor = new LambdaTextEditor();
        final LambdaTextEditorFrame textEditorFrame = new LambdaTextEditorFrame(textEditor);
        final LambdaTextEditorTextView textEditorTextView = 
            new LambdaTextEditorTextView(textEditor);
        
        if (args.length == 0) {
            textEditorTextView.run();
        } else if (args.length == 1 && "--gui".equals(args[0])) {
            textEditorFrame.display();
        } else if (args.length == 1 && "--help".equals(args[0])) {
            printHelp();
        } else if (args.length == 2 && "-c".equals(args[0])) {
            textEditorTextView.runText(args[1]);
        } else if (args.length == 1) {
            textEditorTextView.runFile(args[0]);
        } else {
            printError();
        }
    }

    private static void printError() {
        System.err.println("Error");
    }

    private static void printHelp() {
        System.out.println("Help");
    }
    
}
