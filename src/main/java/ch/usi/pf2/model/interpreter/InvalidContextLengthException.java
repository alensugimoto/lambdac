package ch.usi.pf2.model.interpreter;


/**
 * InvalidContextLengthException is an IllegalArgumentException
 * that is thrown whenever the current context does not have
 * a valid length.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class InvalidContextLengthException extends IllegalArgumentException {

    /**
     * Constructs an invalid-context-length exception with a detail message
     * indicating the expected and actual context lengths.
     * @param expected the expected context length
     * @param actual the actual context length
     */
    public InvalidContextLengthException(final int expected, final int actual) {
        super("Expected a context length of " + expected + ", but got " + actual);
    }

}
