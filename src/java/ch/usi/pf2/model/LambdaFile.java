package ch.usi.pf2.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A LambdaFile represents a file containing text written in the untyped lambda calculus.
 * It is mutable and observable.
 */
public final class LambdaFile {

    private final ArrayList<LambdaFileListener> listeners;
    private String name;
    private final LambdaText text;
    
    /**
     * Create a new LambdaFile with no name.
     */
    public LambdaFile() {
        this(null, new LambdaText());
    }

    /**
     * Create a new LambdaFile with the given file name.
     * @param name the initial file name
     * @param text the initial file text
     */
    public LambdaFile(final String name, final LambdaText text) {
        listeners = new ArrayList<>();
        this.name = name;
        this.text = text;
    }
    
    /**
     * Get the current name of this LambdaFile.
     * @return the current name of this LambdaFile
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Set the name of this LambdaFile.
     * @param name the new name of this LambdaFile
     */
    public final void setName(final String name) {
        this.name = name;
        fireFileNameChanged();
    }
    
    /**
     * Get the current LambdaText of this LambdaFile.
     * @return the current LambdaText of this LambdaFile
     */
    public final LambdaText getText() {
        return text;
    }

    public final void open() throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(name))) {
            String line = reader.readLine();
            String expression = "";
            while (line != null) {
                expression += line + "\n";
            }
            text.setTextToInterpret(expression);
        }
    }

    public final void save() throws IOException {
        if (name == null) {
            throw new UnsupportedOperationException();
        }
        try (final FileWriter writer = new FileWriter(name)) {
            writer.write(text.getTextToInterpret());
        }
    }
    
    //--- listener management
    /**
     * Register the given LambdaFileListener,
     * so it will be notified whenever this LambdaFile changes.
     * @param li The LambdaFileListener to register
     */
    public final void addLambdaFileListener(final LambdaFileListener li) {
        listeners.add(li);
    }
    
    /**
     * Unregister the given LambdaFileListener,
     * so it will not be notified anymore whenever this LambdaFile changes.
     * @param li The LambdaFileListener to unregister
     */
    public final void removeLambdaFileListener(final LambdaFileListener li) {
        listeners.remove(li);
    }
    
    /**
     * Notify all registered LambdaFileListeners that this LambdaFile has changed.
     */
    private void fireFileNameChanged() {
        for (final LambdaFileListener li : listeners) {
            li.fileNameChanged(name);
        }
    }
    
}
