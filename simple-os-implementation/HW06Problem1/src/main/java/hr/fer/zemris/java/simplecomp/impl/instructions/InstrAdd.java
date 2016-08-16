package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction adds values of two registers and stores the result in third register.
 * @author Tomislav
 *
 */
public class InstrAdd implements Instruction {

	/**
	 * Number of register which will hold result of instruction.
	 */
	private int indexRegister1;
	/**
	 * Number of register which holds first value needed for add operation.
	 */
	private int indexRegister2;
	/**
	 * Number of register which holds second value needed for add operation.
	 */
	private int indexRegister3;

	/**
	 * Constructor with one parameter. Parameter is a list of instruction arguments.
	 * @param arguments Instruction arguments.
	 * @throws IllegalArgumentException If number of arguments is not 3.
	 * If some argument is not valid computer register.
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if(arguments.size()!=3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		if(!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}
		this.indexRegister1 = ((Integer)arguments.get(0).getValue()).intValue();
		this.indexRegister2 = ((Integer)arguments.get(1).getValue()).intValue();
		this.indexRegister3 = ((Integer)arguments.get(2).getValue()).intValue();
	}

	/**
	 * Method executes instruction.
	 * @return True if processor needs to be halted, false otherwise.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegister2);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegister3);
		computer.getRegisters().setRegisterValue(
				indexRegister1, Integer.valueOf(
						((Integer)value1).intValue() + ((Integer)value2).intValue()));
		return false;
	}

}
