package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Instruction halts processor.
 * @author Tomislav
 *
 */

public class InstrHalt implements Instruction {
	
	/**
	 * Constructor with one parameter. Parameter is a list of instruction arguments.
	 * @param arguments Instruction arguments.
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		super();
	}
	
	/**
	 * Method executes instruction.
	 * @return True if processor needs to be halted, false otherwise.
	 */
	public boolean execute(Computer computer) {
		return true;
	}
}
