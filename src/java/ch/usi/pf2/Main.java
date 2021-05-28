package ch.usi.pf2;

import ch.usi.pf2.gui.LambdaTextEditorFrame;
import ch.usi.pf2.model.LambdaTextEditor;
import ch.usi.pf2.tui.TextualUserInterface;

import java.awt.EventQueue;


/**
 * The class containing the application's main() method.
 */
public final class Main {
    
    private Main() {
    }
    
    /**
     * Starts the untyped-lambda-calculus-interpreter application.
     * 
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        final LambdaTextEditor textEditor = new LambdaTextEditor();

        if (args.length == 0) {
            showTextualUserInterface(textEditor);
        } else if (args.length == 1 && ("--gui".equals(args[0]) || "-g".equals(args[0]))) {
            showGraphicalUserInterface(textEditor);
        } else if (args.length == 1 && ("--help".equals(args[0]) || "-h".equals(args[0]))) {
            printHelp();
        } else if (args.length == 2 && "-c".equals(args[0])) {
            new TextualUserInterface(textEditor).runText(args[1]);
        } else if (args.length == 1) {
            new TextualUserInterface(textEditor).runFile(args[0]);
        } else {
            System.err.println("Unknown command");
        }
        
    }
    
    private static void showTextualUserInterface(final LambdaTextEditor textEditor) {
        new TextualUserInterface(textEditor).run();
    }
    
    private static void showGraphicalUserInterface(final LambdaTextEditor textEditor) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LambdaTextEditorFrame(textEditor).setVisible(true);
            }
        });
    }
    
    private static void printHelp() {
        System.out.println("Help");
    }
    
}
