package ch.usi.pf2.gui;

import ch.usi.pf2.model.Interpreter;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


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
     * @param interpreter the model to show
     */
    public ReplArea(final Interpreter interpreter) {
        super();
        this.interpreter = interpreter;
        undoManager = new UndoManager();
        showWelcome();
        nextPrompt();
        
        ((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
            
            @Override
            public void insertString(
                final FilterBypass fb,
                final int offset,
                final String string, 
                final AttributeSet attr
            ) throws BadLocationException {
                if (offset >= promptPosition) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        
            @Override
            public void remove(final FilterBypass fb, final int offset, final int length)
                throws BadLocationException {
                if (offset >= promptPosition) {
                    super.remove(fb, offset, length);
                }
            }
        
            @Override
            public void replace(
                final FilterBypass fb, 
                final int offset, 
                final int length, 
                final String text,
                final AttributeSet attrs
            ) throws BadLocationException {
                if (offset >= promptPosition) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
            
        });
        
        final KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);
        final KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
        
        // register listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        select(promptPosition, getText().length());
                        if (getSelectedText() != null && !getSelectedText().trim().isEmpty()) {
                            repl(getSelectedText());
                            e.consume();
                        }
                        setSelectionStart(getText().length());
                        break;
                    case KeyEvent.VK_UP:
                        replaceRange("", promptPosition, getText().length());
                        break;
                    case KeyEvent.VK_DOWN:
                        replaceRange("", promptPosition, getText().length());
                        break;
                    default:
                        break;
                }
            }
        });
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
    
    /**
     * Shows the specified result in a new text area.
     * 
     * @param result the result to show
     */
    public void send(final String result) {
        promptPosition = 0;
        setText("");
        showWelcome();
        append("\n" + result);
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
    
    private void showWelcome() {
        append(interpreter.getWelcomeMessage());
    }
    
}
