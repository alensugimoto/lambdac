/**
 * IDIV divides the second-to-top value by the top value
 * of the OperandStack,
 * and pushes the result back to the OperandStack.
 */
public class IDIV extends Instruction {
    
    @Override
    public void execute(final Storage storage) {
        final OperandStack stack = storage.getOperandStack();
        final int secondOperand = stack.pop();
        final int firstOperand = stack.pop();
        stack.push(firstOperand / secondOperand);
    }
    
    @Override
    public String toString() {
        return "IDIV";
    }

}
