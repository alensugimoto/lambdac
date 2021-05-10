import java.util.List;

/**
 * An abstract syntax tree (AST) node.
 */
public abstract class Node {
    
    private final int position;
    
    public Node(final int position) {
        this.position = position;
    }
    
    protected int getPosition() {
        return position;
    }
    
    /**
     * Evaluate this node.
     * @return an evaluated node
     */
    public Node evaluate() {
        return this;
    }
    
    public Node apply(Node right) {
        return new Application(position, this, right);
    }
    
    public Node termShift(final int d) {
        return termShift(0, d);
    }
    
    public Node termSubst(final int j, final Node s) {
        return termSubst(j, 0, s);
    }
    
    public Node termSubstTop(final Node s) {
        return termSubst(0, s.termShift(1)).termShift(-1);
    }
    
    abstract public Node termShift(final int c, final int d);
    
    abstract public Node termSubst(final int j, final int c, final Node s);
    
    /**
     * Decompile this node into a string.
     * Note that the resulting string may have
     * extra parentheses.
     * @return a String representation of this AST
     */
    abstract public String toString(final List<String> context);
    
}
