package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * This class implements execution unit of computer.
 * Class task is to run microprocessor's program.
 * @author Tomislav
 *
 */

public class ExecutionUnitImpl implements ExecutionUnit {

	/**
	 * Method which task is to run program stored in computer's memory.
	 * @return True if program ended successfully, false otherwise.
	 */
	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		while(true) {
			Instruction instruction = (Instruction)computer.getMemory().getLocation(
					computer.getRegisters().getProgramCounter());
			computer.getRegisters().setProgramCounter(
					computer.getRegisters().getProgramCounter()+1);
			if(instruction.execute(computer)) {
				break;
			}
		}
		return true;
	}
}
