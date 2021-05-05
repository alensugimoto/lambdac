/**
 * An integer multiplication.
 */
public class Multiplication extends Node {
    
    private final Node leftChild;
    private final Node rightChild;
    

    /**
     * Create a new Multiplication node.
     * @param leftChild the left operand
     * @param rightChild the right operand
     */
    public Multiplication(final Node leftChild, final Node rightChild) {
        super();
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public Type getType() {
        return Type.INT;
    }
    
    @Override
    public boolean isConstant() {
        return leftChild.isConstant() && rightChild.isConstant();
    }
    
    @Override
    public void compile(final Program p) {
        leftChild.compile(p);
        rightChild.compile(p);
        p.append(new IMUL());
    }

    @Override
    public String toString() {
        return "(" + leftChild + "*" + rightChild + ")";
    }
    
}
