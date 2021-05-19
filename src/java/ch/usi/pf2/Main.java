package ch.usi.pf2;

import ch.usi.pf2.gui.LambdaFrame;
import ch.usi.pf2.model.Interpreter;

import java.awt.EventQueue;
import java.util.Scanner;


public final class Main {
    
    private Main() {
    }
    
    /**
     * Starts the untyped-lambda-calculus-interpreter application.
     * 
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        if (args.length == 1 && args[0].equals("--gui")) {
            runGUI();
        } else {
            runTUI();
        }
    }
    
    private static void runTUI() {
        final Scanner input = new Scanner(System.in);
        final Interpreter interpreter = new Interpreter();
        String line = input.nextLine();
        while (!"end".equals(line)) {
            System.out.println(interpreter.interpret(line));
            line = input.nextLine();
        }
    }
    
    private static void runGUI() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LambdaFrame().setVisible(true);
            }
        });
    }
    
}
