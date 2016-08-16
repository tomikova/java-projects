package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction tests if values stored in two registers are equal.
 * @author Tomislav
 *
 */

public class InstrTestEquals implements Instruction {

	/**
	 * Number of first register.
	 */
	private int indexRegister1;
	/**
	 * Number of second register.
	 */
	private int indexRegister2;

	/**
	 * Constructor with one parameter. Parameter is a list of instruction arguments.
	 * @param arguments Instruction arguments.
	 * @throws IllegalArgumentException If number of arguments is not 2.
	 * If some argument is not valid computer register.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if(arguments.size()!=2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.indexRegister1 = ((Integer)arguments.get(0).getValue()).intValue();
		this.indexRegister2 = ((Integer)arguments.get(1).getValue()).intValue();
	}

	/**
	 * Method executes instruction.
	 * @return True if processor needs to be halted, false otherwise.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegister1);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegister2);

		if(value1 instanceof String && value2 instanceof String){
			if (((String)value1).equals(((String)value2))) {
				computer.getRegisters().setFlag(true);
			}
			else {
				computer.getRegisters().setFlag(false);
			}
		}
		else if(value1 instanceof Integer && value2 instanceof Integer) {
			if (((Integer)value1).intValue() == ((Integer)value2).intValue()) {
				computer.getRegisters().setFlag(true);
			}
			else {
				computer.getRegisters().setFlag(false);
			}
		}
		else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}
}
