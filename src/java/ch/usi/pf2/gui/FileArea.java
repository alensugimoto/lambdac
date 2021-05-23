package ch.usi.pf2.gui;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


/**
 * The area where file text is written.
 */
public final class FileArea extends JTextArea {
    
    private static final Dimension PREFERRED_SIZE = new Dimension(400, 300);
    
    private File file;
    private final UndoManager undoManager;

    /**
     * Constructs a new FileArea with an untitled file.
     */
    public FileArea() {
        super();
        undoManager = new UndoManager();
        file = null;
        
        final KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);
        final KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
        
        // register listeners
        getDocument().addUndoableEditListener(undoManager);
        getInputMap().put(undoKeyStroke, "undoKeyStroke");
        getActionMap().put("undoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                undo();
            }
        });
        getInputMap().put(redoKeyStroke, "redoKeyStroke");
        getActionMap().put("redoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                redo();
            }
        });
    }
    
    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }
    
    /**
     * Returns the currently opened file.
     * If no file is currently opened, null is returned.
     * @return the currently opened file
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Creates a file containing the specified lambda term.
     * 
     * @param filename the name of the file to write
     * @param term the lambda term to write in the file
     * @return true if successful and false otherwise
     */
    private boolean saveFile(final File file) {
        boolean success = false;
        try (final FileWriter writer = new FileWriter(file)) {
            writer.write(getText());
            this.file = file;
            success = true;
        } catch (IOException ex) {
            System.err.println("There was a problem writing to " + file.getName());
        }
        return success;
    }

    private boolean openFile(final File file) {
        boolean success = false;
        try (FileReader reader = new FileReader(file)) {
            read(reader, null);
            this.file = file;
            success = true;
        } catch (IOException ex) {
            System.err.println("There was a problem reading from " + file.getName());
        }
        return success;
    }
    
    /**
     * Opens a file into this file area.
     */
    public void open() {
        final JFileChooser open = new JFileChooser();
        open.showOpenDialog(null);
        final File selectedFile = open.getSelectedFile(); 
        openFile(selectedFile);
    }

    /**
     * Saves the text area into the file associated with this file area.
     * If the file is null, a save-as operation is performed.
     */
    public void save() {
        if (file == null) {
            saveAs();
        } else {
            saveFile(file);
        }
    }

    /**
     * Saves the text area into a new file.
     */
    public void saveAs() {
        final JFileChooser saveAs = new JFileChooser();
        saveAs.showSaveDialog(null);
        final File selectedFile = saveAs.getSelectedFile();
        int confirmationResult;
        if (selectedFile.exists()) {
            confirmationResult = JOptionPane.showConfirmDialog(this, "Replace existing file?");
            if (confirmationResult == JOptionPane.YES_OPTION) {
                saveFile(selectedFile);                       
            }
        } else {
            saveFile(selectedFile);
        }
    }

    /**
     * Undoes an edit in this text area.
     */
    public void undo() {
        try {
            undoManager.undo();
        } catch (CannotUndoException ex) {
            System.err.println("There was a problem while undoing");
        }
    }

    /**
     * Redoes an edit in this text area.
     */
    public void redo() {
        try {
            undoManager.redo();
        } catch (CannotUndoException ex) {
            System.err.println("There was a problem while undoing");
        }
    }
    
}
