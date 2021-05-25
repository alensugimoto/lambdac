package ch.usi.pf2;

import ch.usi.pf2.gui.GraphicalUserInterface;
import ch.usi.pf2.model.Interpreter;
import ch.usi.pf2.model.parser.ParseException;
import ch.usi.pf2.tui.TextualUserInterface;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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
        final Interpreter interpreter = new Interpreter();
        
        if (args.length == 0) {
            showTextualUserInterface(interpreter);
        } else if (args.length == 1) {
            if ("--gui".equals(args[0])) {
                showGraphicalUserInterface(interpreter);
            } else if ("--help".equals(args[0])) {
                printHelp();
            } else {
                readFromFile(interpreter, args[0]);
            }
        } else if (args.length == 2) {
            if ("-c".equals(args[0])) {
                runString(interpreter, args[1]);
            } else {
                saveToFile(args[0], args[1]);
            }
        } else {
            badArguments(args);
        }
    }
    
    private static void badArguments(final String[] args) {
        System.err.println(
            "Unknown arguments: " + args
            + "\nUsage: lambdac [--gui | --help] [filename | string]"
            + "\nTry `lambdac --help` for more information.");
    }
    
    private static void showTextualUserInterface(final Interpreter interpreter) {
        new TextualUserInterface(interpreter).run();
    }
    
    private static void showGraphicalUserInterface(final Interpreter interpreter) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraphicalUserInterface(interpreter).setVisible(true);
            }
        });
    }
    
    /**
     * Creates a file containing the specified lambda term.
     * 
     * @param filename the name of the file to write
     * @param term the lambda term to write in the file
     * @return true if successful and false otherwise
     */
    private static boolean saveToFile(final String filename, final String term) {
        boolean success = false;
        try (final FileWriter writer = new FileWriter(filename)) {
            writer.write(term);
            success = true;
        } catch (IOException ex) {
            System.err.println("There was a problem writing to " + filename);
        }
        return success;
    }
    
    /**
     * Creates a file containing the specified lambda term.
     * 
     * @param filename the name of the file to write
     * @param term the lambda term to write in the file
     * @return true if successful and false otherwise
     */
    private static boolean readFromFile(final Interpreter interpreter, final String filename) {
        boolean success = false;
        try (final BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String term = reader.readLine();
            while (term != null) {
                runString(interpreter, term);
                term = reader.readLine();
            }
            success = true;
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open " + filename);
        } catch (IOException ex) {
            System.err.println("A problem was encountered reading " + filename);
        }
        return success;
    }
    
    private static void runString(final Interpreter interpreter, final String string) {
        try {
            System.out.println(interpreter.interpret(string));
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void printHelp() {
        System.out.println("Help");
    }
    
}
