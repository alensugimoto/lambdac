package ch.usi.pf2.model;

import java.util.ArrayList;

import ch.usi.pf2.model.parser.ParseException;

/**
 * The LambdaTextEditor is the "model" of this application.
 * It is an immutable class that
 * points to the two mutable and observable parts of the "model":
 * a LambdaText and a LambdaFile.
 */
public final class LambdaTextEditor {

    public static final String NAME = "Lambdac";
    public static final String VERSION = "Version 1.0";

    private final ArrayList<LambdaTextEditorListener> listeners;
    private final LambdaText text;
    private final LambdaFile file;
    private String lastResult;
    
    
    /**
     * Create a new LambdaTextEdit associated with no LambdaText or LambdaFile.
     */
    public LambdaTextEditor() {
        this(new LambdaText(), new LambdaFile());
    }
    
    /**
     * Create a new LambdaTextEdit associated with the given LambdaText and LambdaFile.
     * @param text The LambdaText to edit
     * @param file The LambdaFile over which to edit
     */
    public LambdaTextEditor(final LambdaText text, final LambdaFile file) {
        listeners = new ArrayList<>();
        this.text = text;
        this.file = file;
        lastResult = null;
    }
    
    /**
     * Get the LambdaText of this LambdaTextEdit.
     * @return the LambdaText of this LambdaTextEdit
     */
    public final LambdaText getText() {
        return text;
    }
    
    /**
     * Get the LambdaFile of this LambdaTextEdit.
     * @return the LambdaFile of this LambdaTextEdit
     */
    public final LambdaFile getFile() {
        return file;
    }

    /**
     * Get the LambdaFile of this LambdaTextEdit.
     * @return the LambdaFile of this LambdaTextEdit
     */
    public final String getLastResult() {
        return lastResult;
    }

    public final void runFile() throws ParseException {
        setLastResult(file.getText().interpret());
    }

    public final void runText() throws ParseException {
        setLastResult(text.interpret());
    }

    private final void setLastResult(final String lastResult) {
        this.lastResult = lastResult;
        fireLambdaTextEditorChanged();
    }

    //--- listener management
    /**
     * Register the given LambdaFileListener,
     * so it will be notified whenever this LambdaFile changes.
     * @param li The LambdaFileListener to register
     */
    public final void addLambdaTextEditorListener(final LambdaTextEditorListener li) {
        listeners.add(li);
    }
    
    /**
     * Unregister the given LambdaFileListener,
     * so it will not be notified anymore whenever this LambdaFile changes.
     * @param li The LambdaFileListener to unregister
     */
    public final void removeLambdaTextEditorListener(final LambdaTextEditorListener li) {
        listeners.remove(li);
    }
    
    /**
     * Notify all registered LambdaFileListeners that this LambdaFile has changed.
     */
    private void fireLambdaTextEditorChanged() {
        for (final LambdaTextEditorListener li : listeners) {
            li.lambdaTextEditorChanged(this);
        }
    }
    
}
