package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Observer class which implements IntegerStorageObserver interface.
 * Observer is calculating square value of value stored in IntegerStorage.
 * @author Tomislav
 *
 */
public class SquareValue implements IntegerStorageObserver {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange){
		int value = istorageChange.getValueCurrent();
		System.out.println("Provided new value: "+value+", square is "+value*value);
	}
}
