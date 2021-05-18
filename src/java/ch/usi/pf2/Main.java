package ch.usi.pf2;

import java.util.Scanner;
import ch.usi.pf2.model.Interpreter;

public class Main {
    
    private Main() {
    }
    
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
