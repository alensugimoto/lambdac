/**
 * ILOAD pushes the value of the given variable 
 * onto the operand stack.
 */
public class ILOAD extends Instruction {
    
    private final String name;
    
    
    /**
     * Create a new ILOAD instruction.
     * @param name the name of the variable to be loaded
     */
    public ILOAD(final String name) {
        super();
        this.name = name;
    }
    
    @Override
    public void execute(final Storage storage) {
        storage.getOperandStack().push(storage.getVariableTable().get(name));
    }
    
    @Override
    public String toString() {
        return "ILOAD " + name;
    }

}
