package ch.usi.pf2.gui;

import ch.usi.pf2.model.Interpreter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import javax.swing.border.EmptyBorder;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * The graphical user interface.
 */
public final class GraphicalUserInterface extends JFrame {
    
    private static final String NAME = "Lambdac";
    private static final String VERSION = "Version 1.0";

    private final Interpreter interpreter;
    
    /**
     * Constructs a new graphical user interface.
     */
    public GraphicalUserInterface(final Interpreter interpreter) {
        super(NAME);
        this.interpreter = interpreter;
        makeFrame();
    }
    
    /**
     * Initializes the GUI.
     */
    private void makeFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 10, 10, 10));

        makeMenuBar();
        
        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(8, 8));
        
        // Create definitions area
        /*
        final JTextField expressionField = new JTextField();
        contentPane.add(expressionField, BorderLayout.CENTER);
        expressionField.addActionListener(ev -> {
            final String text = expressionField.getText();
            System.out.println(interpreter.interpret(text));
            expressionField.setText("");
        });
        */
        
        // Create interactive area
        final JTextField expressionField = new JTextField();
        contentPane.add(expressionField, BorderLayout.SOUTH);
        expressionField.addActionListener(e -> {
            final String text = expressionField.getText();
            System.out.println(interpreter.interpret(text));
            expressionField.setText("");
        });
        
        // building is done - arrange the components
        pack();
        
        // place this frame at the center of the screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width/2 - getWidth()/2, d.height/2 - getHeight()/2);
    }
    
    /**
     * Create the main frame's menu bar.
     */
    private void makeMenuBar() {
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("File");
        menubar.add(menu);
        
        item = new JMenuItem("Quit");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener(e -> quit());
        menu.add(item);

        // create the Help menu
        menu = new JMenu("Help");
        menubar.add(menu);
        
        item = new JMenuItem("About " + NAME + "...");
        item.addActionListener(e -> showAbout());
        menu.add(item);
    }
    
    /**
     * About function: show the 'about' box.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(
            this, 
            NAME + "\n" + VERSION,
            "About " + NAME, 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Quit function: quit the application.
     */
    private void quit() {
        System.exit(0);
    }
    
}
