package ch.usi.pf2;

import ch.usi.pf2.gui.LambdacGraphicView;
import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.tui.LambdacTextView;

/**
 * The class containing the application's main() method.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class Main {

    private static final String GUI_OPTION = "--gui";
    private static final String HELP_OPTION = "--help";
    private static final String RUN_OPTION = "-c";

    private Main() {
    }
    
    /**
     * Constructs the model and then either the textual or graphical
     * user interface based on the specified arguments.
     * @param args the command line arguments
     */
    public static final void main(final String[] args) {
        final LambdacModel model = new LambdacModel();
        if (args.length == 0) {
            new LambdacTextView(model).run();
        } else if (args.length == 1 && GUI_OPTION.equals(args[0])) {
            new LambdacGraphicView(model).display();
        } else if (args.length == 1 && HELP_OPTION.equals(args[0])) {
            printHelp();
        } else if (args.length == 2 && RUN_OPTION.equals(args[0])) {
            new LambdacTextView(model).runText(args[1]);
        } else if (args.length == 1) {
            new LambdacTextView(model).runFile(args[0]);
        } else {
            printError();
        }
    }

    private static final void printError() {
        System.err.println("Unknown arguments");
        printUsage();
        System.out.format("Try '%s' for more information.%n", RUN_OPTION);
    }

    private static final void printHelp() {
        printUsage();
        System.out.println("Arguments that may be passed:");
        System.out.println("''        : runs the textual user interface of this application");
        System.out.format("'%s'   : runs the graphical user interface of this application%n",
                          GUI_OPTION);
        System.out.format("'%s'  : prints this help message%n", HELP_OPTION);
        System.out.format("'%s' text : interprets the specified text and prints the result%n",
                          RUN_OPTION);
        System.out.println("file      : interprets the specified file and prints the result");
    }

    private static final void printUsage() {
        System.out.format("Usage: [ '%s' | '%s' | ( '%s' text ) | file ]%n",
                          GUI_OPTION, HELP_OPTION, RUN_OPTION);
    }
    
}
