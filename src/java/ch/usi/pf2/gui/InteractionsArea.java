package ch.usi.pf2.gui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.LambdacModelListener;
import ch.usi.pf2.model.parser.ParseException;

/**
 * The InteractionsArea is part of the GUI.
 * It is a custom component, which draws itself
 * (in the paintComponent method)
 * and which provides information on how big it would like to be
 * (via the getPreferredSize method).
 */
public final class InteractionsArea extends JTextArea {
    
    private static final Dimension PREFERRED_SIZE = new Dimension(400, 300);
    
    private final LambdacModel model;

    private int promptPosition;

    /**
     * Create a InteractionsArea for the given text.
     * @param text the text to show
     */
    public InteractionsArea(final LambdacModel model) {
        super();
        this.model = model;
        refresh(null);
        
        // register listeners
        model.addPropertyChangeListener(new LambdacModelListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LambdacModel.TEXT_TO_INTERPRET_PROPERTY_NAME)) {
                    if (isFocusOwner()) {
                        if (!evt.getNewValue().equals(getPrompt())) {
                            setPrompt(evt.getNewValue().toString());
                        }
                    }
                } else if (evt.getPropertyName().equals(LambdacModel.INTERPRETED_TEXT_PROPERTY_NAME)) {
                    if (isFocusOwner()) {
                        append("\n" + evt.getNewValue());
                        nextPrompt();
                    } else {
                        refresh(evt.getNewValue().toString());
                    }
                }
            }
            
        });
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    final String prompt = getPrompt();
                    if (prompt != null && !prompt.trim().isEmpty()) {
                        try {
                            model.interpret();
                        } catch (ParseException ex) {
                            model.setInterpretedText(ex.getMessage());
                        }
                        model.setTextToInterpret("");
                        e.consume();
                    }
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

    private void refresh(final String interpretedText) {
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
        public void insertString(final FilterBypass fb, final int offset, final String string,
                                 final AttributeSet attr) throws BadLocationException {
            if (offset >= promptPosition) {
                super.insertString(fb, offset, string, attr);
                model.setTextToInterpret(getPrompt());
            }
        }
    
        @Override
        public void remove(final FilterBypass fb, final int offset,
                           final int length) throws BadLocationException {
            if (offset >= promptPosition) {
                super.remove(fb, offset, length);
                model.setTextToInterpret(getPrompt());
            }
        }
        
        @Override
        public void replace(final FilterBypass fb, final int offset, final int length,
                            final String str, final AttributeSet attrs) 
                            throws BadLocationException {
            if (offset >= promptPosition) {
                super.replace(fb, offset, length, str, attrs);
                model.setTextToInterpret(getPrompt());
            }
        }
    
    }
    
}
