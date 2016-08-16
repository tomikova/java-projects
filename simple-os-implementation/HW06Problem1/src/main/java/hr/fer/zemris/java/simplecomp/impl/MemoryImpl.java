package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Implementation of interface Memory which represents computer's memory.
 * On every memory location can be stored object of arbitrary size.
 * Empty memory on every location has value null.
 * @author Tomislav
 *
 */
public class MemoryImpl implements Memory {
	
	/**
	 * Computer's memory.
	 */
	private Object[] memory;
	
	/**
	 * Constructor with one argument.
	 * @param size Size of computer's memory.
	 */
	public MemoryImpl(int size) {
		this.memory = new Object[size];
	}
	
	/**
	 * Method for setting value on desired location.
	 * @param location Memory location.
	 * @param value Value of an object which will be stored (can be null).
	 */
	@Override
	public void setLocation(int location, Object value) {
		memory[location] = value;	
	}
	
	/**
	 * Method retrieves value from given memory location.
	 * @return Value from given memory location.
	 */
	@Override
	public Object getLocation(int location) {
		return memory[location];
	}

}
