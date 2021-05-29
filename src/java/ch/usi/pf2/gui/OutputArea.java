package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.LambdacModelListener;

import java.beans.PropertyChangeEvent;

import javax.swing.JTextArea;

/**
 * The InteractionsArea is part of the GUI.
 * It is a custom component, which draws itself
 * (in the paintComponent method)
 * and which provides information on how big it would like to be
 * (via the getPreferredSize method).
 */
public final class OutputArea extends JTextArea {
    
    /**
     * Create a InteractionsArea for the given text.
     * @param text the text to show
     */
    public OutputArea(final LambdacModel model) {
        super();
        setEditable(false);
        setText(model.getInterpretedText());
        
        // register listeners
        model.addPropertyChangeListener(new LambdacModelListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LambdacModel.INTERPRETED_TEXT_PROPERTY_NAME)) {
                    setText(evt.getNewValue().toString());
                }
            }
            
        });
    }
    
}
