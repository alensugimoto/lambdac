package ch.usi.pf2;

import ch.usi.pf2.gui.LambdaFrame;
import ch.usi.pf2.model.Interpreter;
import ch.usi.pf2.model.parser.ParseException;
import ch.usi.pf2.tui.TextualUserInterface;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


/**
 * The entry class of this interpreter.
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
        try {
            if (args.length == 0) {
                runTextualUserInterface();
            } else if (args.length == 1) {
                if ("--gui".equals(args[0])) {
                    runGraphicalUserInterface();
                } else if ("--help".equals(args[0])) {
                    printHelp();
                } else {
                    runFile(args[0]);
                }
            } else if (args.length == 2 && "-c".equals(args[0])) {
                runString(args[1]);
            } else {
                throw new IllegalArgumentException(
                    "Unknown arguments: " + args
                    + "\nUsage: lambdac [--gui | --help] [filename | string]"
                    + "\nTry `lambdac --help` for more information.");
            }
        } catch (IllegalArgumentException | FileNotFoundException | ParseException exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }
    
    private static void runTextualUserInterface() {
        final TextualUserInterface tui = new TextualUserInterface();
        tui.run();
    }
    
    private static void runGraphicalUserInterface() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LambdaFrame().setVisible(true);
            }
        });
    }
    
    private static void runFile(final String filePath) throws FileNotFoundException {
        final Scanner scanner = new Scanner(new FileReader(filePath));
        final StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.next());
        }
        scanner.close();
        runString(sb.toString());
    }
    
    private static void runString(final String string) {
        System.out.println(new Interpreter().interpret(string));
    }
    
    private static void printHelp() {
        System.out.println("Help");
    }
    
}
