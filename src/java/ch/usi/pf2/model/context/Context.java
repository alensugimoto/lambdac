package ch.usi.pf2.model.context;

import java.util.Collection;
import java.util.LinkedList;


/**
 * A Context contains information about the current naming context.
 */
public class Context extends LinkedList<String> {
    
    /**
     * Constructs an empty context.
     */
    public Context() {
        super();
    }
    
<<<<<<< HEAD
    public Context(final Context context) {
        super(context);
=======
    /**
     * Constructs a list containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     * 
     * @param c the collection whose elements are to be placed into this context
     * @see LinkedList#LinkedList(Collection)
     */
    public Context(Collection<? extends String> c) {
        super(c);
>>>>>>> 514ff581e4b7257c74151e0434bae840b105c3e3
    }
    
}
