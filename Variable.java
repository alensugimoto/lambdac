/**
 * An integer variable.
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
    public Type getType() {
        return Type.INT;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
    
    @Override
    public void compile(final Program p) {
        p.append(new ILOAD(name));
    }

    @Override
    public String toString() {
        return name;
    }
    
}
