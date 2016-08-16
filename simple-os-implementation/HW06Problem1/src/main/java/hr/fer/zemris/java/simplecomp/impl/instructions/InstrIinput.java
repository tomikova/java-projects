package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction reads integer number specified by user and stores him on given memory location.
 * @author Tomislav
 *
 */

public class InstrIinput implements Instruction {

	/**
	 * Memory location.
	 */
	private int location;

	/**
	 * Constructor with one parameter. Parameter is a list of instruction arguments.
	 * @param arguments Instruction arguments.
	 * @throws IllegalArgumentException If number of arguments is not 1.
	 * If argument is not number.
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.location = ((Integer)arguments.get(0).getValue()).intValue();
	}

	/**
	 * Method executes instruction.
	 * @return True if processor needs to be halted, false otherwise.
	 */
	@Override
	public boolean execute(Computer computer) {
		String line;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));
		try{
			System.out.print("Unesite početni broj: ");
			line = reader.readLine().trim();
			Integer number = Integer.parseInt(line);
			computer.getMemory().setLocation(location, number);
			computer.getRegisters().setFlag(true);
		}catch(Exception ex){
			System.out.println("Unos nije moguće protumačiti kao cijeli broj.");
			computer.getRegisters().setFlag(false);
		}
		return false;
	}
}
