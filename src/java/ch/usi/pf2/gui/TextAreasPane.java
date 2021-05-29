package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The TextAreasPane is part of the graphical user interface.
 * It is a custom panel that contains two areas of text:
 * input and output.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class TextAreasPane extends JPanel {

    private static final int GAP = 6;
    private static final int INPUT_TEXT_ROWS = 20;
    private static final int OUTPUT_TEXT_ROWS = 10;
    private static final int TEXT_COLUMNS = 50;

    private final LambdacModel model;
    private final JTextArea inputArea;
    private final JTextArea outputArea;

    /**
     * Constructs a new TextAreasPane for the specified model.
     * @param model the model to use
     */
    public TextAreasPane(final LambdacModel model) {
        super();
        this.model = model;

        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        setLayout(new BorderLayout(GAP, GAP));
        add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.PAGE_START);
        inputArea = new JTextArea(model.getTextToInterpret(), INPUT_TEXT_ROWS, TEXT_COLUMNS);
        outputArea = new JTextArea(model.getInterpretedText(), OUTPUT_TEXT_ROWS, TEXT_COLUMNS);
        inputArea.setEditable(true);
        outputArea.setEditable(false);
        add(new JScrollPane(inputArea), BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        model.addPropertyChangeListener(new LambdacModelListener());
        inputArea.getDocument().addDocumentListener(new InputDocumentListener());
    }

    private final class LambdacModelListener implements PropertyChangeListener {

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(LambdacModel.TEXT_TO_INTERPRET_PROPERTY)
                && !evt.getNewValue().equals(inputArea.getText())) {
                inputArea.setText(evt.getNewValue().toString());
            } else if (evt.getPropertyName().equals(LambdacModel.INTERPRETED_TEXT_PROPERTY)) {
                outputArea.setText(evt.getNewValue().toString());
            }
        }

    }

    private final class InputDocumentListener implements DocumentListener {

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

    }
    
}
