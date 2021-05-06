/**
 * A variable.
 */
public class Variable extends Node {
    
    private final String name;
    

    /**
     * Create a new Variable node.
     * @param name the name of this node
     */
    public Variable(final String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
