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
 * The area where REPL is located.
 */
public final class ReplArea extends JTextArea {
    
    private static final Dimension PREFERRED_SIZE = new Dimension(400, 100);
    
    private final Interpreter interpreter;
    private final UndoManager undoManager;

    /**
     * Constructs a new ReplArea.
     * @param the model to show
     */
    public ReplArea(final Interpreter interpreter) {
        super();
        this.interpreter = interpreter;
        undoManager = new UndoManager();
        
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
