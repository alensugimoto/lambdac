/**
 * An application.
 */
public class Application extends Node {
    
    private final Node leftChild;
    private final Node rightChild;

    
    /**
     * Create a new Application node.
     * @param leftChild the left operand
     * @param rightChild the right operand
     */
    public Application(final Node leftChild, final Node rightChild) {
        super();
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "(" + leftChild + " " + rightChild + ")";
    }
    
}
