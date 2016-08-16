package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction decrements register value for value of 1.
 * @author Tomislav
 *
 */

public class InstrDecrement implements Instruction {
	
	/**
	 * Computer's register number.
	 */
	private int indexRegister;
	
	/**
	 * Constructor with one parameter. Parameter is a list of instruction arguments.
	 * @param arguments Instruction arguments.
	 * @throws IllegalArgumentException If number of arguments is not 1.
	 * If some argument is not valid computer register.
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.indexRegister = ((Integer)arguments.get(0).getValue()).intValue();
	}
	
	/**
	 * Method executes instruction.
	 * @return True if processor needs to be halted, false otherwise.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(indexRegister);
		computer.getRegisters().setRegisterValue(
				indexRegister, Integer.valueOf(
						((Integer)value).intValue() - 1));
		return false;
	}
}
