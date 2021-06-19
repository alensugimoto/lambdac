package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The TextAreasPane is part of the graphical user interface.
 * It is a custom panel that contains two areas of text:
 * input and output.
 * The input area is where lambda expressions may be written,
 * and the output area shows the result of evaluating an expression
 * written in the input area.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class TextAreasPane extends JPanel {

    private static final int GAP;
    private static final int INPUT_TEXT_ROWS;
    private static final int OUTPUT_TEXT_ROWS;
    private static final int TEXT_COLUMNS;
    private static final String FONT_NAME;
    private static final int FONT_STYLE;
    private static final int FONT_SIZE;

    private final LambdacModel model;
    private final JTextArea inputArea;
    private final JTextArea outputArea;

    static {
        GAP = 6;
        INPUT_TEXT_ROWS = 20;
        OUTPUT_TEXT_ROWS = 10;
        TEXT_COLUMNS = 70;
        FONT_NAME = Font.MONOSPACED;
        FONT_STYLE = Font.PLAIN;
        FONT_SIZE = 12;
    }

    /**
     * Constructs a new TextAreasPane for the specified model.
     * @param model the model to use
     */
    public TextAreasPane(final LambdacModel model) {
        super();
        this.model = model;

        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        setLayout(new BorderLayout(GAP, GAP));
        add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.PAGE_START);
        inputArea = new JTextArea(model.getTextToInterpret(), INPUT_TEXT_ROWS, TEXT_COLUMNS);
        outputArea = new JTextArea(model.getInterpretedText(), OUTPUT_TEXT_ROWS, TEXT_COLUMNS);
        final Font font = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);
        inputArea.setEditable(true);
        inputArea.setFont(font);
        outputArea.setEditable(false);
        outputArea.setFont(font);
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
