package ch.usi.pf2;

import ch.usi.pf2.gui.LambdacGraphicView;
import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.tui.LambdacTextView;


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
        final LambdacModel model = new LambdacModel();
        
        if (args.length == 0) {
            new LambdacTextView(model).run();
        } else if (args.length == 1 && "--gui".equals(args[0])) {
            new LambdacGraphicView(model).display();
        } else if (args.length == 1 && "--help".equals(args[0])) {
            printHelp();
        } else if (args.length == 2 && "-c".equals(args[0])) {
            new LambdacTextView(model).runText(args[1]);
        } else if (args.length == 1) {
            new LambdacTextView(model).runFile(args[0]);
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
