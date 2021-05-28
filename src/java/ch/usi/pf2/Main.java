package ch.usi.pf2;

import ch.usi.pf2.gui.GraphicalUserInterface;
import ch.usi.pf2.gui.LambdaTextEditorFrame;
import ch.usi.pf2.model.Interpreter;
import ch.usi.pf2.model.LambdaTextEditor;
import ch.usi.pf2.model.parser.ParseException;
import ch.usi.pf2.tui.TextualUserInterface;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LambdaTextEditorFrame(textEditor).setVisible(true);
            }
        });
        // if (args.length == 0) {
        //     showTextualUserInterface(interpreter);
        // } else if (args.length == 1 && ("--gui".equals(args[0]) || "-g".equals(args[0]))) {
        //     showGraphicalUserInterface(interpreter);
        // } else if (args.length == 1 && ("--help".equals(args[0]) || "-h".equals(args[0]))) {
        //     printHelp();
        // } else if (args.length == 2 && "-c".equals(args[0])) {
        //     runString(interpreter, args[1]);
        // } else {
        //     readFromFile(interpreter, args[0], Arrays.copyOfRange(args, 1, args.length));
        // }
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
    
    private static void printHelp() {
        System.out.println("Help");
    }
    
    private static void runString(final Interpreter interpreter, final String string) {
        try {
            System.out.println(interpreter.interpret(string));
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // private static boolean readFromFile(
    //     final Interpreter interpreter,
    //     final String filename,
    //     final String... args
    // ) {
    //     boolean success = false;
    //     try {
    //         System.out.println(interpreter.interpretFile(filename, args));
    //         success = true;
    //     } catch (FileNotFoundException ex) {
    //         System.err.println("Unable to open " + filename);
    //     } catch (IOException ex) {
    //         System.err.println("A problem was encountered reading " + filename);
    //     } catch (ParseException ex) {
    //         System.err.println(ex.getMessage());
    //     }
    //     return success;
    // }
    
}
