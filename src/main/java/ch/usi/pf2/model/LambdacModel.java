package ch.usi.pf2.model;

import ch.usi.pf2.model.interpreter.Interpreter;
import ch.usi.pf2.model.parser.ParseException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The LambdacModel is the model of this application
 * and acts as an observable.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class LambdacModel {

    public static final String NAME = "Lambdac";
    public static final String VERSION = "Version 1.0";

    public static final String FILE_PATH_PROPERTY = "FilePath";
    public static final String TEXT_TO_INTERPRET_PROPERTY = "TextToInterpret";
    public static final String INTERPRETED_TEXT_PROPERTY = "InterpretedText";

    private final PropertyChangeSupport support;
    private final Interpreter interpreter;

    private String filePath;
    private String textToInterpret;
    private String interpretedText;
    
    /**
     * Constructs a new LambdacModel.
     */
    public LambdacModel() {
        this(null, "", null);
    }
    
    /**
     * Constructs a new LambdacModel, holding the specified strings.
     * @param filePath the path of the file that is currently in focus
     * @param textToInterpret the text to be interpreted
     * @param interpretedText the text that resulted in the last interpretation
     */
    public LambdacModel(final String filePath, final String textToInterpret,
                        final String interpretedText) {
        support = new PropertyChangeSupport(this);
        interpreter = new Interpreter();
        setFilePath(filePath);
        setTextToInterpret(textToInterpret);
        setInterpretedText(interpretedText);
    }

    /**
     * Sets the contents of the file that is currently in focus
     * as the next text to be interpreted.
     * @throws IOException if an I/O error occurs opening the file in focus
     */
    public final void open() throws IOException {
        final Charset charset = Charset.forName("US-ASCII");
        final Path path = Paths.get(filePath);
        try (final BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            final StringBuilder builder = new StringBuilder();
            while (line != null) {
                builder.append((builder.length() == 0 ? "" : "\n") + line);
                line = reader.readLine();
            }
            setTextToInterpret(builder.toString());
        }
    }

    /**
     * Writes to the file that is currently in focus
     * the text that is to be interpreted.
     * @throws IOException if an I/O error occurs opening the file in focus
     * @throws UnsupportedOperationException if this method is invoked without any file
     *                                       being in focus
     */
    public final void save() throws IOException {
        if (filePath == null) {
            throw new UnsupportedOperationException();
        }
        try (final FileWriter writer = new FileWriter(filePath)) {
            writer.write(getTextToInterpret());
        }
    }

    /**
     * Interprets the text to be interpreted and 
     * sets the result as the text that was interpreted.
     * @throws ParseException if an error occurs parsing the text to interpret
     */
    public final void interpret() throws ParseException {
        setInterpretedText(interpreter.interpret(textToInterpret));
    }

    /**
     * Returns the path of the file in focus.
     * @return the path of the file in focus
     */
    public final String getFilePath() {
        return filePath;
    }

    /**
     * Sets the path of the file in focus to the specified string.
     * @param filePath the new path of the file in focus
     */
    public final void setFilePath(final String filePath) {
        final String oldFilePath = this.filePath;
        this.filePath = filePath;
        support.firePropertyChange(FILE_PATH_PROPERTY, oldFilePath, filePath);
    }

    /**
     * Returns the text to be interpreted.
     * @return the text to be interpreted
     */
    public final String getTextToInterpret() {
        return textToInterpret;
    }

    /**
     * Sets the text to interpret to the specified string.
     * @param textToInterpret the new text to interpret
     */
    public final void setTextToInterpret(final String textToInterpret) {
        final String oldTextToInterpret = this.textToInterpret;
        this.textToInterpret = textToInterpret;
        support.firePropertyChange(TEXT_TO_INTERPRET_PROPERTY, oldTextToInterpret, textToInterpret);
    }

    /**
     * Returns the text that resulted from the last interpretation.
     * @return the text that resulted from the last interpretation
     */
    public final String getInterpretedText() {
        return interpretedText;
    }

    /**
     * Sets the interpreted text to the specified string.
     * @param interpretedText the new interpreted text
     */
    public final void setInterpretedText(final String interpretedText) {
        final String oldInterpretedText = this.interpretedText;
        this.interpretedText = interpretedText;
        support.firePropertyChange(INTERPRETED_TEXT_PROPERTY, oldInterpretedText, interpretedText);
    }

    /**
     * Returns the help message for the untyped lambda calculus.
     * @return the help message for the untyped lambda calculus
     */
    public static final String getHelp() {
        return Interpreter.HELP;
    }

    /**
     * Adds a PropertyChangeListener from the listener list.
     * @param listener the PropertyChangeListener to be added
     */
    public final void addPropertyChangeListener(final PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener from the listener list.
     * @param listener the PropertyChangeListener to be removed
     */
    public final void removePropertyChangeListener(final PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
    
}
