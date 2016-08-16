package hr.fer.zemris.java.tecaj.hw5.observer1;

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
	public void valueChanged(IntegerStorage istorage){
		int value = istorage.getValue();
		System.out.println("Provided new value: "+value+", square is "+value*value);
	}
}
