package ch.usi.pf2.gui;

import ch.usi.pf2.model.Interpreter;
import ch.usi.pf2.model.parser.ParseException;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


/**
 * The main frame of this application.
 */
public final class GraphicalUserInterface extends JFrame {
    
    private final Interpreter interpreter;
    private final FileArea fileArea;
    private final ReplArea replArea;
    
    /**
     * Constructs a new GraphicalUserInterface for the given interpreter.
     * @param interpreter the model to show
     */
    public GraphicalUserInterface(final Interpreter interpreter) {
        super();
        this.interpreter = interpreter;
        fileArea = new FileArea(interpreter);
        replArea = new ReplArea(interpreter);
        makeFrame();
    }
    
    /**
     * Initializes the GUI.
     */
    private void makeFrame() {
        updateTitle();
        makeMenuBar();
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        // add buttons panel
        final JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        final JButton run = new JButton("Run");
        buttons.add(run);
        contentPane.add(buttons, BorderLayout.NORTH);
        
        // add text-areas panel
        final JPanel textAreas = new JPanel();
        textAreas.setLayout(new GridLayout(2, 1, 8, 8));
        textAreas.setBorder(new EmptyBorder(0, 10, 10, 10));
        textAreas.add(fileArea);
        textAreas.add(replArea);
        contentPane.add(textAreas, BorderLayout.CENTER);
        
        // register listeners
        run.addActionListener(e -> {
            replArea.send(interpreter.interpret(fileArea.getText()));
        });
        
        // building is done - arrange the components
        pack();
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
        
        item = new JMenuItem("Open...");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener(e -> {
            fileArea.open();
            updateTitle();
        });
        menu.add(item);

        item = new JMenuItem("Save");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener(e -> {
            fileArea.save();
            updateTitle();
        });
        menu.add(item);

        item = new JMenuItem("Save As...");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener(e -> {
            fileArea.saveAs();
            updateTitle();
        });
        menu.add(item);
        
        item = new JMenuItem("Quit");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener(e -> quit());
        menu.add(item);

        // create the Edit menu
        menu = new JMenu("Edit");
        menubar.add(menu);
        
        item = new JMenuItem("Undo");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UNDO, SHORTCUT_MASK));   
        item.addActionListener(e -> fileArea.undo());
        menu.add(item);

        item = new JMenuItem("Redo");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));   
        item.addActionListener(e -> fileArea.redo());
        menu.add(item);

        item = new JMenuItem("Select All");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));   
        item.addActionListener(e -> fileArea.selectAll());
        menu.add(item);

        item = new JMenuItem("Copy");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COPY, SHORTCUT_MASK));   
        item.addActionListener(e -> fileArea.copy());
        menu.add(item);

        item = new JMenuItem("Paste");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PASTE, SHORTCUT_MASK));   
        item.addActionListener(e -> fileArea.paste());
        menu.add(item);

        item = new JMenuItem("Cut");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_CUT, SHORTCUT_MASK));   
        item.addActionListener(e -> fileArea.cut());
        menu.add(item);

        // create the Help menu
        menu = new JMenu("Help");
        menubar.add(menu);
        
        item = new JMenuItem("About " + interpreter.getName() + "...");
        item.addActionListener(e -> showAbout());
        menu.add(item);
    }
    
    /**
     * About function: show the 'about' box.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(
            this, 
            interpreter.getName() + "\n" + interpreter.getVersion(),
            "About " + interpreter.getName(), 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Quit function: quit the application.
     */
    private void quit() {
        System.exit(0);
    }
    
    private void updateTitle() {
        setTitle((fileArea.getFile() == null ? "Untitled" : fileArea.getFile().getName())
            + " - "
            + interpreter.getName());
    }
    
}
