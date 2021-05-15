import java.util.Scanner;
import model.Interpreter;
import gui.LambdaFrame;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LambdaFrame().setVisible(true);
            }
        });
        /**
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
        */
    }
    
}
