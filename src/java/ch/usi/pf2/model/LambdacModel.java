package ch.usi.pf2.model;

import ch.usi.pf2.model.parser.ParseException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The LambdaTextEditor is the "model" of this application.
 * It is an immutable class that
 * points to the two mutable and observable parts of the "model":
 * a LambdaText and a LambdaFile.
 */
public final class LambdacModel {

    public static final String NAME = "Lambdac";
    public static final String VERSION = "Version 1.0";
    
    public static final String FILE_NAME_PROPERTY = "FileName";
    public static final String TEXT_TO_INTERPRET_PROPERTY = "TextToInterpret";
    public static final String INTERPRETED_TEXT_PROPERTY = "InterpretedText";

    private final PropertyChangeSupport support;
    private final Interpreter interpreter;

    private String fileName;
    private String textToInterpret;
    private String interpretedText;
    
    /**
     * Create a new LambdaTextEdit associated with no LambdaText or LambdaFile.
     */
    public LambdacModel() {
        this(null, "", null);
    }
    
    /**
     * Create a new LambdaTextEdit associated with the given LambdaText and LambdaFile.
     * @param text The LambdaText to edit
     * @param file The LambdaFile over which to edit
     */
    public LambdacModel(final String fileName, final String textToInterpret,
                        final String interpretedText) {
        support = new PropertyChangeSupport(this);
        interpreter = new Interpreter();
        setFileName(fileName);
        setTextToInterpret(textToInterpret);
        setInterpretedText(interpretedText);
    }

    public final void open() throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            String textToInterpret = "";
            while (line != null) {
                textToInterpret += line + "\n";
                line = reader.readLine();
            }
            setTextToInterpret(textToInterpret);
        }
    }

    public final void save() throws IOException {
        if (fileName == null) {
            throw new UnsupportedOperationException();
        }
        try (final FileWriter writer = new FileWriter(fileName)) {
            writer.write(getTextToInterpret());
        }
    }

    /**
     * Interpret this LambdaText.
     * @return the interpretation of this LambdaText
     */
    public final void interpret() throws ParseException {
        setInterpretedText(interpreter.interpret(textToInterpret));
    }

    public final String getFileName() {
        return fileName;
    }

    public final void setFileName(final String fileName) {
        final String oldFileName = this.fileName;
        this.fileName = fileName;
        firePropertyChange(FILE_NAME_PROPERTY, oldFileName, fileName);
    }

    public final String getTextToInterpret() {
        return textToInterpret;
    }

    public final void setTextToInterpret(final String textToInterpret) {
        final String oldTextToInterpret = this.textToInterpret;
        this.textToInterpret = textToInterpret;
        firePropertyChange(TEXT_TO_INTERPRET_PROPERTY, oldTextToInterpret, textToInterpret);
    }

    public final String getInterpretedText() {
        return interpretedText;
    }

    public final void setInterpretedText(final String interpretedText) {
        final String oldInterpretedText = this.interpretedText;
        this.interpretedText = interpretedText;
        firePropertyChange(INTERPRETED_TEXT_PROPERTY, oldInterpretedText, interpretedText);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    private void firePropertyChange(final String propertyName, final Object oldValue,
                                    final Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }
    
}
