package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.Scanner;
import model.Interpreter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;


/**
 * The main frame.
 * The "GUI".
 */
public final class LambdaFrame extends JFrame {

    /**
     * Create a new LambdaFrame.
     */
    public LambdaFrame() {
        super();
        setTitle("λ");
        setLayout(new BorderLayout());
        final JTextField expressionField = new JTextField();
        add(expressionField, BorderLayout.SOUTH);
        expressionField.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent ev) {
                final String text = expressionField.getText();
                System.out.println(new Interpreter().interpret(text));
                expressionField.setText("");
            }
        });
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
    }
}