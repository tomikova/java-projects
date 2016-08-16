package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction sets program counter value to provided jump location if flag value is set to true.
 * @author Tomislav
 *
 */
public class InstrJumpIfTrue implements Instruction {

	/**
	 * Memory jump location.
	 */
	private int jumpLocation;

	/**
	 * Constructor with one parameter. Parameter is a list of instruction arguments.
	 * @param arguments Instruction arguments.
	 * @throws IllegalArgumentException If number of arguments is not 1.
	 * If argument is not number.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.jumpLocation = ((Integer)arguments.get(0).getValue()).intValue();
	}

	/**
	 * Method executes instruction.
	 * @return True if processor needs to be halted, false otherwise.
	 */
	@Override
	public boolean execute(Computer computer) {
		boolean flag = computer.getRegisters().getFlag();
		if(flag) {
			computer.getRegisters().setProgramCounter(jumpLocation);
		}
		return false;
	}
}
