package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * Observer class which implements IntegerStorageObserver interface.
 * Observer is doubling value stored in IntegerStorage until number of IntegerStorage value changes
 * reaches number of two.
 * @author Tomislav
 *
 */

public class DoubleValue implements IntegerStorageObserver {

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
	public DoubleValue() {
		this.counter = DEFAULT;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorage istorage){
		if (counter==2){
			istorage.removeObserver(this);
		}
		else{
			System.out.println("Double value: "+2*istorage.getValue());
			counter++;
		}
	}
}
