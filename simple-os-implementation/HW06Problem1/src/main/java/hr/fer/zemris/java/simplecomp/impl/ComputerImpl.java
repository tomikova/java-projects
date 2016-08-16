package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of Computer interface which represents data part of computer.
 * @author Tomislav
 *
 */

public class ComputerImpl implements Computer {
	
	/**
	 * Computer's memory
	 */
	private Memory memory;
	/**
	 * Computer's registers.
	 */
	private Registers registers;
	
	/**
	 * Constructor with two parameters.
	 * @param memorySize Size of computer's memory.
	 * @param registersSize Number of compuer's registers.
	 */
	public ComputerImpl(int memorySize, int registersSize) {
		this.memory = new MemoryImpl(memorySize);
		this.registers = new RegistersImpl(registersSize);
	}
	
	/**
	 * Method retrieves computer's memory.
	 * @return Computer's memory.
	 */
	@Override
	public Memory getMemory() {
		return memory;
	}

	/**
	 * Method retrieves computer's registers.
	 * @return Computer's registers.
	 */
	@Override
	public Registers getRegisters() {
		return registers;
	}

}
