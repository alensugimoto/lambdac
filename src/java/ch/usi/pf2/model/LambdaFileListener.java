package ch.usi.pf2.model;


/**
 * The "model" doesn't know the specific "GUI" classes.
 * But it still wants to notify the "GUI" (or text UI, or ...)
 * whenever the "model" changes, so that the "GUI" (or text UI, or ...)
 * can react to those changes (e.g., by repainting itself).
 * 
 * <p>To enable this, we have this RangeListener interface.
 * The "model" (i.e., the Range) will call rangeChanged() 
 * on all registered listeners,
 * without knowing the specific subtype of RangeListener
 * (e.g., the "model" does not know about the PlotCanvas class).
 */
@FunctionalInterface
public interface LambdaFileListener {

    /**
     * Notify this LambdaFileListener that the given LambdaFile has changed.
     * @param file The LambdaFile that changed.
     */
    public abstract void fileNameChanged(final String fileName);
    
}
