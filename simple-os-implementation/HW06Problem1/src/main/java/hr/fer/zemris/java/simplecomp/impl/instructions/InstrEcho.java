package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Instruction echoes register value.
 * @author Tomislav
 *
 */

public class InstrEcho implements Instruction {
	
	/**
	 * Computer's register number.
	 */
	private int regIndex;
	
	/**
	 * Constructor with one parameter. Parameter is a list of instruction arguments.
	 * @param arguments Instruction arguments.
	 * @throws IllegalArgumentException If number of arguments is not 1.
	 * If some argument is not valid computer register.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.regIndex = ((Integer)arguments.get(0).getValue()).intValue();
	}
	
	/**
	 * Method executes instruction.
	 * @return True if processor needs to be halted, false otherwise.
	 */
	public boolean execute(Computer computer) {
		System.out.println(computer.getRegisters().getRegisterValue(regIndex));
		return false;
	}
}
