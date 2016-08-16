package hr.fer.zemris.java.tecaj.hw5.observer2;

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
	public void valueChanged(IntegerStorageChange istorageChange){
		if (counter==2){
			istorageChange.getIstorage().removeObserver(this);
		}
		else{
			System.out.println("Double value: "+2*istorageChange.getValueCurrent());
			counter++;
		}
	}
}
