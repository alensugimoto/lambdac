package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.LambdacModelListener;

import java.beans.PropertyChangeEvent;

import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * The DefinitionsArea is part of the GUI.
 * It is a custom component, which draws itself
 * (in the paintComponent method)
 * and which provides information on how big it would like to be
 * (via the getPreferredSize method).
 */
public final class InputArea extends JTextArea {

    private final LambdacModel model;

    /**
     * Create a DefinitionsArea for the given text.
     * @param text The text to show
     */
    public InputArea(final LambdacModel model) {
        super();
        this.model = model;
        setText(model.getTextToInterpret());
        
        // register listeners
        model.addPropertyChangeListener(new LambdacModelListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LambdacModel.TEXT_TO_INTERPRET_PROPERTY_NAME)
                    && !evt.getNewValue().equals(getText())) {
                    setText(evt.getNewValue().toString());
                }
            }
            
        });
        ((AbstractDocument)getDocument()).setDocumentFilter(new LambdaDocumentFilter());
    }

    private class LambdaDocumentFilter extends DocumentFilter {
        
        @Override
        public void insertString(final FilterBypass fb, final int offset, final String string,
                                 final AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, string, attr);
            model.setTextToInterpret(getText());
        }
    
        @Override
        public void remove(final FilterBypass fb, final int offset,
                           final int length) throws BadLocationException {
            super.remove(fb, offset, length);
            model.setTextToInterpret(getText());
        }
        
        @Override
        public void replace(final FilterBypass fb, final int offset, final int length,
                            final String str, final AttributeSet attrs)
                            throws BadLocationException {
            super.replace(fb, offset, length, str, attrs);
            model.setTextToInterpret(getText());
        }
    
    }

}
