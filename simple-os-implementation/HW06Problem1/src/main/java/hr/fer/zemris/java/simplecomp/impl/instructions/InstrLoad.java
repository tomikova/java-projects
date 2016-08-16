package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import java.util.List;

/**
 * Instruction reads value on provided memory location and stores it in register.
 * @author Tomislav
 *
 */

public class InstrLoad implements Instruction {

	/**
	 * Register number.
	 */
	private int regIndex;
	/**
	 * Memory location.
	 */
	private int memLocation;

	/**
	 * Constructor with one parameter. Parameter is a list of instruction arguments.
	 * @param arguments Instruction arguments.
	 * @throws IllegalArgumentException 
	 * If number of arguments is not 2.
	 * If first argument is not valid register. 
	 * If second argument is not number.
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if(arguments.size()!=2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		this.regIndex = ((Integer)arguments.get(0).getValue()).intValue();
		this.memLocation = ((Integer)arguments.get(1).getValue()).intValue();
	}

	/**
	 * Method executes instruction.
	 * @return True if processor needs to be halted, false otherwise.
	 */
	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(memLocation);
		computer.getRegisters().setRegisterValue(regIndex, value);
		return false;
	}
}
