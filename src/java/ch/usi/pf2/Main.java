package ch.usi.pf2;

import ch.usi.pf2.model.Interpreter;

import java.util.Scanner;


public class Main {
    
    /**
     * Starts the untyped-lambda-calculus-interpreter application.
     * 
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        final Scanner input = new Scanner(System.in);
        boolean f = true;
        while (f) {
            final String a = input.nextLine();
            if ("end".equals(a)) {
                f = false;
            } else {
                System.out.println(new Interpreter().interpret(a));
            }
        }
    }
    
}
