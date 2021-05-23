package ch.usi.pf2.gui;

import ch.usi.pf2.model.Interpreter;
import java.awt.event.KeyAdapter;

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
import javax.swing.text.DocumentFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.AbstractDocument;


/**
 * The area where REPL is located.
 */
public final class ReplArea extends JTextArea {
    
    private static final Dimension PREFERRED_SIZE = new Dimension(400, 100);
    
    private final Interpreter interpreter;
    private final UndoManager undoManager;
    private int promptPosition;

    /**
     * Constructs a new ReplArea.
     * @param the model to show
     */
    public ReplArea(final Interpreter interpreter) {
        super();
        this.interpreter = interpreter;
        undoManager = new UndoManager();
        promptPosition = 2;
        append("> ");
        
        ((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
            
            @Override
            public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr) throws BadLocationException {
                if (offset >= promptPosition) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        
            @Override
            public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
                if (offset >= promptPosition) {
                    super.remove(fb, offset, length);
                }
            }
        
            @Override
            public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException {
                if (offset >= promptPosition) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
            
        });
        
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);
        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
        
        // register listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    select(promptPosition, getText().length());
                    if (getSelectedText() != null && !getSelectedText().trim().isEmpty()) {
                        repl(getSelectedText());
                        e.consume();
                    }
                    setSelectionStart(getText().length());
                }
            }
        });
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
    
    public void send(final String result) {
        promptPosition = 0;
        setText(result);
        nextPrompt();
    }
    
    private void repl(final String term) {
        append("\n" + interpreter.interpret(term));
        nextPrompt();
    }
    
    private void nextPrompt() {
        append("\n> ");
        promptPosition = getText().length();
        undoManager.discardAllEdits();
    }
    
}
