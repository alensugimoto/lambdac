package ch.usi.pf2.gui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import ch.usi.pf2.model.LambdaTextEditor;
import ch.usi.pf2.model.LambdaTextListener;
import ch.usi.pf2.model.command.EditHistory;
import ch.usi.pf2.model.command.InterpretCommand;
import ch.usi.pf2.model.command.RedoCommand;
import ch.usi.pf2.model.command.UndoCommand;

/**
 * The InteractionsArea is part of the GUI.
 * It is a custom component, which draws itself
 * (in the paintComponent method)
 * and which provides information on how big it would like to be
 * (via the getPreferredSize method).
 */
public final class InteractionsArea extends JTextArea {
    
    private static final Dimension PREFERRED_SIZE = new Dimension(400, 300);
    
    private final LambdaTextEditor textEditor;
    private final EditHistory history;
    private int promptPosition;

    /**
     * Create a InteractionsArea for the given text.
     * @param text the text to show
     */
    public InteractionsArea(final LambdaTextEditor textEditor) {
        super();
        this.textEditor = textEditor;
        history = new EditHistory();
        setUp(null);
        
        // register listeners
        textEditor.getText().addLambdaTextListener(new LambdaTextListener() {
            @Override
            public void textToInterpretChanged(final String textToInterpret) {
                if (!textToInterpret.equals(getPrompt())) {
                    setPrompt(textToInterpret);
                }
            }
            @Override
            public void interpretedTextChanged(final String interpretedText) {
                append("\n" + interpretedText);
                nextPrompt();
            }
        });
        textEditor.getFile().getText().addLambdaTextListener(new LambdaTextListener() {
            @Override
            public void textToInterpretChanged(final String textToInterpret) {
                // do nothing
            }
            @Override
            public void interpretedTextChanged(final String interpretedText) {
                setUp(interpretedText);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        final String prompt = getPrompt();
                        if (prompt != null && !prompt.trim().isEmpty()) {
                            new InterpretCommand(history, textEditor.getText()).execute();
                            e.consume();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        new UndoCommand(history).execute();
                        e.consume();
                        break;
                    case KeyEvent.VK_DOWN:
                        new RedoCommand(history).execute();
                        e.consume();
                        break;
                    default:
                        break;
                }
            }
        });
        ((AbstractDocument)getDocument()).setDocumentFilter(new LambdaDocumentFilter());
    }
    
    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    private String getPrompt() {
        select(promptPosition, getText().length());
        final String prompt = getSelectedText();
        setSelectionStart(getText().length());
        return prompt == null ? "" : prompt;
    }

    private void setPrompt(final String prompt) {
        replaceRange(prompt, promptPosition, getText().length());
    }

    private void setUp(final String interpretedText) {
        promptPosition = 0;
        setText("");
        showWelcome();
        if (interpretedText != null) {
            append("\n" + interpretedText);
        }
        nextPrompt();
    }
    
    private void nextPrompt() {
        append("\n> ");
        promptPosition = getText().length();
    }
    
    private void showWelcome() {
        append("Welcome");
    }

    private class LambdaDocumentFilter extends DocumentFilter {
        
        @Override
        public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr) throws BadLocationException {
            if (offset >= promptPosition) {
                super.insertString(fb, offset, string, attr);
                textEditor.getText().setTextToInterpret(getPrompt());
            }
        }
    
        @Override
        public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
            if (offset >= promptPosition) {
                super.remove(fb, offset, length);
                textEditor.getText().setTextToInterpret(getPrompt());
            }
        }
        
        @Override
        public void replace(final FilterBypass fb, final int offset, final int length, final String str, final AttributeSet attrs) throws BadLocationException {
            if (offset >= promptPosition) {
                super.replace(fb, offset, length, str, attrs);
                textEditor.getText().setTextToInterpret(getPrompt());
            }
        }
    
    }
    
}
