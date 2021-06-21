package ch.usi.pf2.model.interpreter;

import java.util.Collection;
import java.util.LinkedList;


/**
 * A Context contains information about the current naming context.
 * The string at index {@code i} is a string hint
 * for the name of the variable with a de Bruijn index of {@code i}.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class Context extends LinkedList<String> {
    
    /**
     * Constructs an empty context.
     */
    public Context() {
        super();
    }
    
    /**
     * Constructs a list containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     * @param c the collection whose elements are to be placed into this context
     * @see LinkedList#LinkedList(Collection)
     */
    public Context(final Collection<? extends String> c) {
        super(c);
    }

    /**
     * Inserts the specified string, possibly appended with a few primes,
     * at the beginning of this context such that it is a unique
     * element in the new context.
     * @param arg the string to add
     */
    public final void addFirstUnique(final String arg) {
        final StringBuilder builder = new StringBuilder(arg);
        while (contains(builder.toString())) {
            builder.append("'");
        }
        addFirst(builder.toString());
    }
    
}
