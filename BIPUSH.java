/**
 * BIPUSH pushes the given value onto the operand stack.
 */
public class BIPUSH extends Instruction {
    
    private final int value;
    
    
    /**
     * Create a new BIPUSH instruction.
     * @param value the integer value to be pushed
     */
    public BIPUSH(final int value) {
        super();
        this.value = value;
    }
    
    @Override
    public void execute(final Storage storage) {
        storage.getOperandStack().push(value);
    }
    
    @Override
    public String toString() {
        return "BIPUSH " + value;
    }

}
