package ch.usi.pf2.gui;

import ch.usi.pf2.model.Interpreter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.Event;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import java.io.File;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


/**
 * The area where file text is written.
 */
public final class FileArea extends JTextArea {
    
    private static final Dimension PREFERRED_SIZE = new Dimension(400, 300);

    private final Interpreter interpreter;
    private File file;
    private final UndoManager undoManager;
    private boolean saved = false;

    /**
     * Constructs a new FileArea with an untitled file.
     * @param interpreter the model to show
     */
    public FileArea(final Interpreter interpreter) {
        super();
        this.interpreter = interpreter;
        undoManager = new UndoManager();
        file = null;
        saved = false;
        
        setSize(PREFERRED_SIZE);
        
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);
        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
        
        // register listeners
        getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        getInputMap().put(undoKeyStroke, "undoKeyStroke");
        getActionMap().put("undoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        getInputMap().put(redoKeyStroke, "redoKeyStroke");
        getActionMap().put("redoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
    
    public boolean saveFile(final File file) {
        boolean success = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(getText());
            success = true;
            saved = true;
        } catch (IOException err) {
            err.printStackTrace();
        }
        return success;
    }

    public boolean quickSave() {
        boolean success = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(getText());
            success = true;
        } catch (IOException err) {
            err.printStackTrace();
        }
        return success;
    }

    public boolean openingFiles(final File file) {
        boolean success = false;
        try (FileReader reader = new FileReader(file)) {
            this.file = file;
            read(reader, null);
            success = true;
        } catch (IOException err) {
            err.printStackTrace();
        }
        return success;
    }
    
    /**
     * Creates a file containing the specified lambda term.
     * 
     * @param filename the name of the file to write
     * @param term the lambda term to write in the file
     * @return true if successful and false otherwise
     */
    public void open() {
        JFileChooser open = new JFileChooser();
        open.showOpenDialog(null);
        final File selectedFile = open.getSelectedFile(); 
        openingFiles(selectedFile);
    }

    public void save() {
        JFileChooser save = new JFileChooser();
        if (file == null) {
            save.showSaveDialog(null);
            final File selectedFile = save.getSelectedFile();
            int confirmationResult;
            if (selectedFile.exists()) {
                confirmationResult = JOptionPane.showConfirmDialog(this, "Replace existing file?");
                if (confirmationResult == JOptionPane.YES_OPTION) {
                    saveFile(selectedFile);
                }
            } else {
                saveFile(selectedFile);
            }
        } else {
            quickSave();
        }
    }

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

    public void undo() {
        try {
            undoManager.undo();
        } catch (CannotUndoException cur) {
            cur.printStackTrace();
        }
    }

    public void redo() {
        try {
            undoManager.redo();
        } catch (CannotUndoException cur) {
            cur.printStackTrace();
        }
    }
    
}
