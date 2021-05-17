package ch.usi.pf2;

import java.util.Scanner;
import java.awt.EventQueue;
import javax.swing.JFrame;

import ch.usi.pf2.model.Interpreter;
import ch.usi.pf2.gui.LambdaFrame;

public class Main {
    
    public static void main(final String[] args) {
        Scanner input = new Scanner(System.in);
        boolean f = true;
        while (f) {
            String a = input.nextLine();
            if (a.equals("end")) {
                f = false;
            } else {
                System.out.println(new Interpreter().interpret(a));
            }
        }
    }
    
}
