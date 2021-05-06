/**
 * An abstraction.
 */
public class Abstraction extends Node {
    
    private final Node leftChild;
    private final Node rightChild;

    
    /**
     * Create a new Abstraction node.
     * @param leftChild the left operand
     * @param rightChild the right operand
     */
    public Abstraction(final Node leftChild, final Node rightChild) {
        super();
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "(\\" + leftChild + "." + rightChild + ")";
    }
    
}
