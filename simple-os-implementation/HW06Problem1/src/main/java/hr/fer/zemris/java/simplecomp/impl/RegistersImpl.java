package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of Registers interface which represents all computer's registers.
 * Registers can store objects of arbitrary size.
 * @author Tomislav
 *
 */

public class RegistersImpl implements Registers {
	
	/**
	 * Program counter.
	 */
	private int programCounter;
	
	/**
	 * Flag used to determine if expression is true or not.
	 */
	private boolean flag;
	
	/**
	 * Computer's registers.
	 */
	private Object[] registers;
	
	/**
	 * Constructor with one parameter.
	 * @param regsLen Number of computer's registers.
	 */
	public RegistersImpl(int regsLen) {
		this.registers = new Object[regsLen];
	}
	
	/**
	 * Method retrieves object stored in register.
	 * @param index Register number.
	 * @return Object stored in register.
	 */
	@Override
	public Object getRegisterValue(int index) {
		return registers[index];
	}
	
	/**
	 * Method stores object in register.
	 * @param index Register number.
	 * @param value Object which will be stored.
	 */
	@Override
	public void setRegisterValue(int index, Object value) {
		registers[index] = value;
		
	}
	
	/**
	 * Method retrieves value of program counter.
	 * @return Value of program counter.
	 */
	@Override
	public int getProgramCounter() {
		return programCounter;
	}
	
	/**
	 * Method for setting new value of program counter.
	 * @param value New value of program counter.
	 */
	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
		
	}
	
	/**
	 * Method increments program counter for value of 1.
	 */
	@Override
	public void incrementProgramCounter() {
		programCounter++;
		
	}
	
	/**
	 * Method retrieves flag value.
	 * @return Flag value.
	 */
	@Override
	public boolean getFlag() {
		return flag;
	}
	
	/**
	 * Method for setting new flag value.
	 * @param value New flag value.
	 */
	@Override
	public void setFlag(boolean value) {
		flag = value;
	}
}
