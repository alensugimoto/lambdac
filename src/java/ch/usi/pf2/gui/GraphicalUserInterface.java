package ch.usi.pf2.gui;

import ch.usi.pf2.model.Interpreter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;


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
        fileArea = new FileArea();
        replArea = new ReplArea(interpreter);
        makeFrame();
    }
    
    /**
     * Initializes the GUI.
     */
    private void makeFrame() {
        updateTitle();
        makeMenuBar();
        final JPanel contentPane = (JPanel) getContentPane();
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
        final int shortcutMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        final JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("File");
        menubar.add(menu);
        
        item = new JMenuItem("Open...");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutMask));
        item.addActionListener(e -> {
            fileArea.open();
            updateTitle();
        });
        menu.add(item);

        item = new JMenuItem("Save");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutMask));
        item.addActionListener(e -> {
            fileArea.save();
            updateTitle();
        });
        menu.add(item);

        item = new JMenuItem("Save As...");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutMask));
        item.addActionListener(e -> {
            fileArea.saveAs();
            updateTitle();
        });
        menu.add(item);
        
        item = new JMenuItem("Quit");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutMask));
        item.addActionListener(e -> quit());
        menu.add(item);

        // create the Edit menu
        menu = new JMenu("Edit");
        menubar.add(menu);
        
        item = new JMenuItem("Undo");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UNDO, shortcutMask));   
        item.addActionListener(e -> fileArea.undo());
        menu.add(item);

        item = new JMenuItem("Redo");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutMask));   
        item.addActionListener(e -> fileArea.redo());
        menu.add(item);

        item = new JMenuItem("Select All");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutMask));   
        item.addActionListener(e -> fileArea.selectAll());
        menu.add(item);

        item = new JMenuItem("Copy");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COPY, shortcutMask));   
        item.addActionListener(e -> fileArea.copy());
        menu.add(item);

        item = new JMenuItem("Paste");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PASTE, shortcutMask));   
        item.addActionListener(e -> fileArea.paste());
        menu.add(item);

        item = new JMenuItem("Cut");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_CUT, shortcutMask));   
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
