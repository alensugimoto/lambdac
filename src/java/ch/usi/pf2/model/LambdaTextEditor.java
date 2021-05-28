package ch.usi.pf2.model;


/**
 * The LambdaTextEditor is the "model" of this application.
 * It is an immutable class that
 * points to the two mutable and observable parts of the "model":
 * a LambdaText and a LambdaFile.
 */
public final class LambdaTextEditor {

    private static final String NAME = "Lambdac";
    private static final String VERSION = "Version 1.0";

    private final LambdaText text;
    private final LambdaFile file;
    
    
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
        this.text = text;
        this.file = file;
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

    public final String getName() {
        return NAME;
    }

    public final String getVersion() {
        return VERSION;
    }
    
}
