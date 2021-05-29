package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.LambdacModelListener;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextAreasPanel extends JPanel {

    public TextAreasPanel(final LambdacModel model) {
        super();

        setLayout(new GridLayout(0, 1));
        final JTextArea inputArea = new JTextArea(model.getTextToInterpret());
        final JTextArea outputArea = new JTextArea(model.getInterpretedText());
        inputArea.setEditable(true);
        outputArea.setEditable(false);
        add(inputArea);
        add(outputArea);

        model.addPropertyChangeListener(new LambdacModelListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LambdacModel.TEXT_TO_INTERPRET_PROPERTY)
                    && !evt.getNewValue().equals(inputArea.getText())) {
                    inputArea.setText(evt.getNewValue().toString());
                } else if (evt.getPropertyName().equals(LambdacModel.INTERPRETED_TEXT_PROPERTY)) {
                    outputArea.setText(evt.getNewValue().toString());
                }
            }
            
        });
        inputArea.getDocument().addDocumentListener(new DocumentListener() {
           
            @Override
            public void insertUpdate(final DocumentEvent e) {
                model.setTextToInterpret(inputArea.getText());
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                model.setTextToInterpret(inputArea.getText());
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                model.setTextToInterpret(inputArea.getText());
            }

        });
    }
    
}
