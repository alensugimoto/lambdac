package ch.usi.pf2.model.interpreter;


/**
 * InvalidContextException is an RuntimeException
 * that is thrown whenever the current context is invalid
 * in any way.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class InvalidContextException extends IllegalArgumentException {

    /**
     * Constructs an invalid context exception with
     * the the invalid context.
     * @param context the invalid context
     */
    public InvalidContextException(final Context context) {
        super("Context is invalid: " + context);
    }

}
