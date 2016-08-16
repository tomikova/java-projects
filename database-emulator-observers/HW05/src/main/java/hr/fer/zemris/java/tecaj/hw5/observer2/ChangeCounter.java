package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Observer class which implements IntegerStorageObserver interface.
 * Observer is counting how many times value in IntegerStorage has changed.
 * @author Tomislav
 *
 */

public class ChangeCounter implements IntegerStorageObserver {
	
	/**
	 * Default count.
	 */
	private static final int DEFAULT = 0;
	/**
	 * Counter of IntegerStorage value changes.
	 */
	private int counter;
	
	/**
	 * Constructor without parameters. Initializes counter to default value.
	 */
	public ChangeCounter() {
		this.counter = DEFAULT;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange){
		counter++;
		System.out.println("Number of value changes since tracking: "+counter);
	}
}
