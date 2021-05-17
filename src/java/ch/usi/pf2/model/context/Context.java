package ch.usi.pf2.model.context;

import java.util.LinkedList;

/**
 * A Context contains information about the current naming context.
 */
public class Context extends LinkedList<String> {
    
    public Context() {
        super();
    }
    
    public Context(Context context) {
        super(context);
    }
    
}
